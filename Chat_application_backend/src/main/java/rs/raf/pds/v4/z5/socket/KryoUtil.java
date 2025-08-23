package rs.raf.pds.v4.z5.socket;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class KryoUtil {

    private static final Kryo kryo = new Kryo();

    static {
        kryo.register(SocketMessages.class);
    }

    public static byte[] serialize(Object obj) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, obj);
        output.close();
        return baos.toByteArray();
    }

    public static <T> T deserialize(byte[] data, Class<T> type) {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Input input = new Input(bais);
        T obj = kryo.readObject(input, type);
        input.close();
        return obj;
    }
}
