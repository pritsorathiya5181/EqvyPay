package com.eqvypay;

import com.eqvypay.command_line_runner.CommandLineRunner;

public class EqvyPayApplication {
 public static void main(String[] args) throws Exception {
	 CommandLineRunner commandLineRunner = new CommandLineRunner();
		try {
			commandLineRunner.commandLineRunner();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

}
}
