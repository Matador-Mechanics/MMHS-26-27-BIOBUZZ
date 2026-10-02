package org.firstinspires.ftc.teamcode.globals;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import Ori.Coval.Logging.AutoLogManager;
import Ori.Coval.Logging.Logger.KoalaLog;

public class Robot extends com.seattlesolvers.solverslib.command.Robot { //based on https://github.com/FTC-23511/Decode-2026/blob/master/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/globals/Robot.java
	HardwareMap hardwareMap = null;
	OpMode opMode = null;

	public void init(OpMode opmode, HardwareMap hwMap){
		hardwareMap = hwMap;
		opMode = opmode;
		KoalaLog.setup(hardwareMap);
		KoalaLog.start();
	}

	public void updateLoop() {
		AutoLogManager.periodic();
	}
}
