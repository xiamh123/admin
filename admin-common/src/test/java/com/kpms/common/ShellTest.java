package com.kpms.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShellTest {

	public static void main(String[] args) {

		try {
			String shpath = "/Users/xiamh/test.sh";
			Process ps = Runtime.getRuntime().exec(shpath);
			ps.waitFor();

			BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			String result = sb.toString();
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
