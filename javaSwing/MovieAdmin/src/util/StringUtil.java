package util;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtil {
	//넘겨받은 숫자가 1자리수이면 앞에 0을 붙이기 ex)05
	//누군가 이 메서드를 호출하면 처리결과를 반환받는다
	public static String getNumString(int num) {
		String str;
		if(num < 10) { // 한자리
			str = "0"+num;
		}else { //두자리
			str = Integer.toString(num); // wrapper 적용
		}
		return str;
	} 
	
	//확장자 추출, 반환받기
	public static String getExtend(String filename) {
		//String filename="ddaddddda.png";
		int lastInedex = filename.lastIndexOf(".");
		System.out.println(lastInedex);
		
		
		return filename.substring(lastInedex+1, filename.length());
	}
	
	
	//비밀번호 암호화
	//java의 보안과 관련된 기능을 지원하는 api가 모여있는 패키지 : java.security
	public static String getCovertedPass(String pass) {
        //암호화 객체
        StringBuffer hexString=null;
        try {
            MessageDigest digest=MessageDigest.getInstance("SHA-256");
            byte[] hash=digest.digest(pass.getBytes("UTF-8"));  //(암호화대상)

             //String은 불변임 따라서 그 값이 변경될 수 없음
            //따라서 String 객체는 반복문 등에서 반복문 횟수가 클 경우 절대 누적식을 사용해서는 안됨
            //해결책) 변경가능한 문자열 객체를 지원하는 StringBuffer, StringBuilder 등을 활용
            hexString=new StringBuffer();   //StringBuffer는 String이 아님 그러므로 = 사용 불가

            for(int i=0;i<hash.length;i++) {
                String hex=Integer.toHexString(0xff&hash[i]);
                //System.out.println(hash[i]);
                System.out.println(hex);
                if(hex.length()==1) {
                    hexString.append("0");
                }
                //hexString+=hex;
                hexString.append(hex);
            }
            System.out.println(hexString.toString());
            System.out.println(hexString.length());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return hexString.toString();
    }

	
	//파일명 수정 후 반환
	public static String createFilename(String url) {
		//url 정보를 받아와서 파일명을 수정한다
		long time = System.currentTimeMillis();
		
		//확장자 구하기
		String ext = StringUtil.getExtend(url);
		
		return time+"."+ext;
	}

//	public static void main(String[] args) {
//		String result = getConvertedPassword("minzino");
//		System.out.println(result.length());
//	}

}
