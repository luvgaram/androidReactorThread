import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


public class Demultiplexer implements Runnable{

	private final int HEADER_SIZE = 6;
	
	private Socket socket;
	private HandleMap handleMap;
	
	public Demultiplexer(Socket socket, HandleMap handleMap) {
		this.socket = socket;
		this.handleMap = handleMap;
	}
	
	@Override
	public void run() {
		try {
			InputStream inputStream = socket.getInputStream();
			
			byte[] buffer = new byte[HEADER_SIZE];
			inputStream.read(buffer);
			String header = new String(buffer);
			
			handleMap.get(header).handleEvent(inputStream); // hashmap 메소드: 지정한 키에 해당하는 데이터 반환
			
			socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
