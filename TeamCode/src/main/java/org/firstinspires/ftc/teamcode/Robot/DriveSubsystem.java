package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.drivebase.MecanumDrive;
import com.seattlesolvers.solverslib.drivebase.RobotDrive;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

public class DriveSubsystem extends SubsystemBase {
    private MotorEx fL, fR, bL, bR;
    private MecanumDrive dT;
    DriveSubsystem(final HardwareMap hMap, final String frontLeft, final String frontRight, final String backLeft, final String backRight) {
        fL = new MotorEx(hMap, frontLeft);
        fR = new MotorEx(hMap, frontRight);
        bL = new MotorEx(hMap, backLeft);
        bR = new MotorEx(hMap, backRight);

        dT = new MecanumDrive(fL, fR, bL, bR);
    }
    @Override
    public void periodic() {
        dT.driveFieldCentric(hMap.Gamepad1);
    }

}
