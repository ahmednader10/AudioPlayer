package exercise2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Exercise2 {
	public static double max(double R,double G,double B) {
		double temp = Math.max(R, G);
		return Math.max(temp,B);
	}
	public static double min(double R,double G,double B) {
		double temp = Math.min(R, G);
		return Math.min(temp,B);
	}
	public static void RGB_to_HSV(double R,double G,double B) {
		
		
		 R = R / 255.0;
		
		
		 G = G / 255.0;
		
		
		 B = B / 255.0;
		
		
		double H = 0;
		double S = 0;
		double max = max(R, G, B);
		double min = min(R, G, B);
		if(max == min) {
			H = 0;
		}
		else if (max == R && G >= B) {
			H = 60*((G - B)/(max - min));
			if (H >= 360) {
				H -= 360;
			}
	
		}
		else if (max == R && G < B){
			H = (60*((G - B)/(max - min)))+360;
			if (H >= 360) {
				H -= 360;
			}
		
		}
		else if (max == G) {
			H = (60*((B - R)/(max - min)))+120;
			if (H >= 360) {
				H -= 360;
			}
		
		}
		else if (max == B) {
			H = (60*((R - G)/(max - min)))+240;
			if (H >= 360) {
				H -= 360;
			}
		
		}
		
		if (max == 0) {
			S = 0;
		}
		else {
			S = (max - min) / max;
			S *= 100;
		}
		double V = max*100;
		System.out.println("(H,S,V) in percentages = (" +H+" ,"+S+"% ,"+V+"%)" );
		
	}
	public static void HSV_to_RGB(double H,double S,double V) {
		
		double R = 0;
		double G = 0;
		double B = 0;
		if (H >= 360) {
			H -= 360;
		}
		
		
			S = S / 100.0;
		
			V = V / 100.0;
		
		int Hi = (int)H / 60;
		double f = (H / 60.0) - Hi;
		double p = V*(1 - S);
		double q = V*(1 - (f*S));
		double t = V*(1 - (1 - f)*S);
		if (Hi == 0) {
			R = V;
			G = t;
			B = p;
		}
		if (Hi == 1) {
			R = q;
			G = V;
			B = p;
		}
		if (Hi == 2) {
			R = p;
			G = V;
			B = t;
		}
		if (Hi == 3) {
			R = p;
			G = q;
			B = V;
		}
		if (Hi == 4) {
			R = t;
			G = p;
			B = V;
		}
		if (Hi == 5) {
			R = V;
			G = p;
			B = q;
		}
		R *= 255;
		G *= 255;
		B *= 255;
		System.out.println("(R,G,B) = (" + R+","+G+","+B+")");
		
		
	}
	public static void main(String[]args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter 1 to transform from RGB to HSV and 2 to transform from HSV to RGB :");
		String line = br.readLine();
		if (line.equals("1")) {
			System.out.println("Enter the value of R :");
			double R = Double.parseDouble(br.readLine());
			System.out.println("Enter the value of G :");
			double G = Double.parseDouble(br.readLine());
			System.out.println("Enter the value of B :");
			double B = Double.parseDouble(br.readLine());
			RGB_to_HSV(R, G, B);
		}
		else {
			if (line.equals("2")) {
				System.out.println("Enter the value of H :");
				double H = Double.parseDouble(br.readLine());
				System.out.println("Enter the value of S :");
				double S = Double.parseDouble(br.readLine());
				System.out.println("Enter the value of V :");
				double V = Double.parseDouble(br.readLine());
				HSV_to_RGB(H,S,V);
			}
		
		else {
			System.out.println("You should enter 1 or 2");
		}
	
	} 
	}

}
