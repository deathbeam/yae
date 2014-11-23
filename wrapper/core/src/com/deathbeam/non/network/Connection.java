package com.deathbeam.non.network;

import com.deathbeam.non.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Represents a connection between two computers.
 * 
 * @author Jason
 * 
 */
public abstract class Connection {
    private static final int MAGIC_NUMBER = 1304231989;
    private long bytesSent = 0;
    protected Listener listener;
    private final byte[] headerInput = new byte[8];
    private final byte[] headerOutput = new byte[8];

    public abstract boolean isConnected();
    public abstract void send(ByteArrayOutputStream data);
    protected abstract InputStream getTCPInputStream();
    protected abstract OutputStream getTCPOutputStream();
    public abstract void close();

    public Connection(Listener listener) {
        if (listener == null) Utils.warning("network", "You must supply a connection listener.");
        this.listener = listener;
    }

    protected byte[] readTCP() throws IOException, Exception {
        InputStream tcpInput = getTCPInputStream();
        if (tcpInput.read(headerInput) == -1) return null;
        int magicNumber = ByteBuffer.wrap(headerInput).getInt();
        if (magicNumber != MAGIC_NUMBER) Utils.warning("network", "Bad magic number: " + magicNumber);
        int len = ByteBuffer.wrap(headerInput).getInt(4);
        byte[] data = new byte[len];
        int count = 0;
        while (count < len) {
            count += tcpInput.read(data, count, len - count);
        }
        
        return decompress(data);
    }

    protected void sendTCP(byte[] data) throws IOException {
        OutputStream tcpOutput = getTCPOutputStream();

        data = compress(data);
        ByteBuffer.wrap(headerOutput).putInt(MAGIC_NUMBER);
        ByteBuffer.wrap(headerOutput).putInt(4, data.length);
        tcpOutput.write(headerOutput);
        tcpOutput.write(data);
        tcpOutput.flush();

        bytesSent += data.length;
    }

    protected byte[] compress(byte[] data) {
        Deflater compressor = new Deflater();
        compressor.setInput(data);
        compressor.finish();

        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        byte[] buf = new byte[1024];
        while (!compressor.finished()) {
            int count = compressor.deflate(buf);
            bos.write(buf, 0, count);
        }

        return bos.toByteArray();
    }

    protected byte[] decompress(byte[] data) {
        Inflater decompressor = new Inflater();
        decompressor.setInput(data);

        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);

        byte[] buf = new byte[1024];
        while (!decompressor.finished()) {
            try {
                int count = decompressor.inflate(buf);
                bos.write(buf, 0, count);
            } catch (DataFormatException e) {
                throw new RuntimeException(e);
            }
        }

        return bos.toByteArray();
    }

    public void setConnectionListener(Listener listener) {
        this.listener = listener;
    }

    public long getBytesSent() {
        return bytesSent;
    }
}