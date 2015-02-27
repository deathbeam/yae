package non;

import java.io.*;

public class Buffer {
    private ByteArrayInputStream inBytes;
    private ByteArrayOutputStream outBytes;
    private DataInputStream in;
    private DataOutputStream out;

    public Buffer() {
        outBytes = new ByteArrayOutputStream();
        out = new DataOutputStream(outBytes);
    }

    public Buffer(byte[] data) {
        inBytes = new ByteArrayInputStream(data);
        in = new DataInputStream(inBytes);
    }

    public Buffer write(byte[] data) throws IOException {
        out.write(data);
        return this;
    }

    public Buffer writeBoolean(boolean val) throws IOException {
        out.writeBoolean(val);
        return this;
    }

    public Buffer writeByte(int val) throws IOException {
        out.writeByte(val);
        return this;
    }

    public Buffer writeFloat(float val) throws IOException {
        out.writeFloat(val);
        return this;
    }

    public Buffer writeInt(int val) throws IOException {
        out.writeInt(val);
        return this;
    }

    public Buffer writeLong(long val) throws IOException {
        out.writeLong(val);
        return this;
    }

    public Buffer writeString(String val) throws IOException {
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