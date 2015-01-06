package non;

import java.io.*;

public class NonBuffer {
    private ByteArrayInputStream inBytes;
    private ByteArrayOutputStream outBytes;
    private DataInputStream in;
    private DataOutputStream out;

    public NonBuffer() {
        outBytes = new ByteArrayOutputStream();
        out = new DataOutputStream(outBytes);
    }

    public NonBuffer(byte[] data) {
        inBytes = new ByteArrayInputStream(data);
        in = new DataInputStream(inBytes);
    }

    public NonBuffer write(byte[] data) throws IOException {
        out.write(data);
        return this;
    }

    public NonBuffer writeBoolean(boolean val) throws IOException {
        out.writeBoolean(val);
        return this;
    }

    public NonBuffer writeByte(int val) throws IOException {
        out.writeByte(val);
        return this;
    }

    public NonBuffer writeFloat(float val) throws IOException {
        out.writeFloat(val);
        return this;
    }

    public NonBuffer writeInt(int val) throws IOException {
        out.writeInt(val);
        return this;
    }

    public NonBuffer writeLong(long val) throws IOException {
        out.writeLong(val);
        return this;
    }

    public NonBuffer writeString(String val) throws IOException {
        out.writeUTF(val);
        return this;
    }

    public byte[] read() {
        return outBytes.toByteArray();
    }

    public boolean readBoolean() throws IOException {
        return in.readBoolean();
    }

    public byte readByte() throws IOException {
        return in.readByte();
    }

    public float readFloat() throws IOException {
        return in.readFloat();
    }

    public int readInt() throws IOException {
        return in.readInt();
    }

    public long readLong() throws IOException {
        return in.readLong();
    }

    public String readString() throws IOException {
        return in.readUTF();
    }
}