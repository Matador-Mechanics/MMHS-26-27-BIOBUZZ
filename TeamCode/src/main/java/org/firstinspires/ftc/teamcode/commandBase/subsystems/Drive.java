package org.firstinspires.ftc.teamcode.commandBase.subsystems;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.drivebase.MecanumDrive;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.globals.constants;

public class Drive extends SubsystemBase {
    private MotorEx fL, fR, bL, bR;
    private MecanumDrive dT;
    private GoBildaPinpointDriver Pinpoint;
    private OpMode intOpMode;
    private GamepadEx gamepad1;
    public Drive(final OpMode OpMode) {
        intOpMode = OpMode;

        fL = new MotorEx(intOpMode.hardwareMap, constants.hardwareStrings.get("fL"));
        fR = new MotorEx(intOpMode.hardwareMap, constants.hardwareStrings.get("fR"));
        bL = new MotorEx(intOpMode.hardwareMap, constants.hardwareStrings.get("bL"));
        bR = new MotorEx(intOpMode.hardwareMap, constants.hardwareStrings.get("bR"));

        dT = new MecanumDrive(fL, fR, bL, bR);

        Pinpoint = intOpMode.hardwareMap.get(GoBildaPinpointDriver.class, constants.hardwareStrings.get("pinpoint"));
    }
    @Override
    public void periodic() {
        gamepad1 = new GamepadEx(intOpMode.gamepad1);
        dT.driveFieldCentric(gamepad1.getLeftX(), gamepad1.getLeftY(), gamepad1.getRightX(), Pinpoint.getHeading(AngleUnit.DEGREES));
    }
}
