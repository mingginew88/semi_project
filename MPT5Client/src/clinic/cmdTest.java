package clinic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class cmdTest {


	public void start() {
		  String s;

		  try {
		    /*  자바 1.4 이하에서는 이렇게
		    Runtime oRuntime = Runtime.getRuntime();
		    Process oProcess = oRuntime.exec("cmd /c dir /?");
		    */

		    // 자바 1.5 이상에서는 이렇게 1줄로
		    Process oProcess = new ProcessBuilder("cmd", "/c", "start chrome https://raduck.github.io/").start();

		    // 외부 프로그램 출력 읽기
		    BufferedReader stdOut   = new BufferedReader(new InputStreamReader(oProcess.getInputStream()));
		    BufferedReader stdError = new BufferedReader(new InputStreamReader(oProcess.getErrorStream()));

		    // "표준 출력"과 "표준 에러 출력"을 출력
		    while ((s =   stdOut.readLine()) != null) System.out.println(s);
		    while ((s = stdError.readLine()) != null) System.err.println(s);

		    // 외부 프로그램 반환값 출력 (이 부분은 필수가 아님)
		    System.out.println("Exit Code: " + oProcess.exitValue());
//		    System.exit(oProcess.exitValue()); // 외부 프로그램의 반환값을, 이 자바 프로그램 자체의 반환값으로 삼기

		  } catch (IOException e) { // 에러 처리
		      System.err.println("에러! 외부 명령 실행에 실패했습니다.\n" + e.getMessage());
		      System.exit(-1);
		    }
		  }
	
			

	}

