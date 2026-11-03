package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.seattlesolvers.solverslib.drivebase.MecanumDrive;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.psilynx.psikit.ftc.autolog.PsiKitAutoLog;

@PsiKitAutoLog
//@Disabled
@TeleOp(name = "TeleOp", group = "TeleOp")
public class Teleop extends OpMode {

    MotorEx fL, fR, bL, bR;
    MecanumDrive mecanumDrive;
    GamepadEx GP1;
    boolean robotDrive = true;

    @Override
    public void init() {
        fL = new MotorEx(hardwareMap, "frontLeft");
        fL.motor.setDirection(DcMotorSimple.Direction.REVERSE);
        fR = new MotorEx(hardwareMap, "frontRight");
        fL.motor.setDirection(DcMotorSimple.Direction.REVERSE);
        bL = new MotorEx(hardwareMap, "backLeft");
        fL.motor.setDirection(DcMotorSimple.Direction.FORWARD);
        bR = new MotorEx(hardwareMap, "backRight");
        fL.motor.setDirection(DcMotorSimple.Direction.REVERSE);

        mecanumDrive = new MecanumDrive(fL, fR, bL, bR);
    }

    @Override
    public void loop() {
        GP1 = new GamepadEx(gamepad1);
        mecanumDrive.driveRobotCentric(GP1.getRightX(), -GP1.getRightY(), GP1.getLeftX(), true);
    }
    @Override
    public void stop(){

    }
}
