package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.drivebase.MecanumDrive;
import com.seattlesolvers.solverslib.drivebase.RobotDrive;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class DriveSubsystem extends SubsystemBase {
    private MotorEx fL, fR, bL, bR;
    private MecanumDrive dT;
    private GoBildaPinpointDriver Pinpoint;
    private HardwareMap hMap = null;
    private GamepadEx gamepad1 = null;
    DriveSubsystem(final HardwareMap hardwareMap, final Gamepad gamepad, final String frontLeft, final String frontRight, final String backLeft, final String backRight, final String pinpoint) {
        hMap = hardwareMap;
        gamepad1 = new GamepadEx(gamepad);
        fL = new MotorEx(hMap, frontLeft);
        fR = new MotorEx(hMap, frontRight);
        bL = new MotorEx(hMap, backLeft);
        bR = new MotorEx(hMap, backRight);

        dT = new MecanumDrive(fL, fR, bL, bR);

        Pinpoint = hMap.get(GoBildaPinpointDriver.class, pinpoint);
    }
    @Override
    public void periodic() {
        dT.driveFieldCentric(this.gamepad1.getLeftX(), this.gamepad1.getLeftY(), this.gamepad1.getRightX(), Pinpoint.getHeading(AngleUnit.DEGREES), true);
    }
}
