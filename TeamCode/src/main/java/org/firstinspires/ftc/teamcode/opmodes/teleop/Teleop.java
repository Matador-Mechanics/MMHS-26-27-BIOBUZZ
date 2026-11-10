package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.seattlesolvers.solverslib.drivebase.MecanumDrive;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.globals.constants;
import org.psilynx.psikit.core.Logger;
import org.psilynx.psikit.ftc.autolog.PsiKitAutoLog;

@PsiKitAutoLog
//@Disabled
@TeleOp(name = "TeleOp", group = "TeleOp")
public class Teleop extends OpMode {

    MotorEx fL, fR, bL, bR, IntakeMotor, PollenMotor;
    MecanumDrive mecanumDrive;
    GamepadEx GP1;
    VoltageSensor voltageSensor;
    GamepadEx GP1Old;

    @Override
    public void init() {
        fL = new MotorEx(hardwareMap, "frontLeft");
        fL.motor.setDirection(DcMotorSimple.Direction.REVERSE);
        fR = new MotorEx(hardwareMap, "frontRight");
        fR.motor.setDirection(DcMotorSimple.Direction.FORWARD);
        bL = new MotorEx(hardwareMap, "backLeft");
        bL.motor.setDirection(DcMotorSimple.Direction.REVERSE);
        bR = new MotorEx(hardwareMap, "backRight");
        bR.motor.setDirection(DcMotorSimple.Direction.REVERSE);

        mecanumDrive = new MecanumDrive(fL, fR, bL, bR);

        IntakeMotor = new MotorEx(hardwareMap, "IntakeMotor");
        IntakeMotor.setRunMode(Motor.RunMode.VelocityControl);
        IntakeMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        IntakeMotor.setInverted(false);

        PollenMotor = new MotorEx(hardwareMap, "PollenMotor");
        PollenMotor.setRunMode(Motor.RunMode.VelocityControl);
        PollenMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        PollenMotor.setInverted(true);

        voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");

        GP1 = new GamepadEx(gamepad1);
    }

    @Override
    public void loop() {
        logger();
        GP1Old = GP1;
        GP1 = new GamepadEx(gamepad1);
        if (GP1 != GP1Old) {
            //drive();
        }
        if (GP1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1) {
            IntakeMotor.setVelocity(ticksToVelocity(constants.INTAKESPEED,5));
        } else {
            IntakeMotor.stopMotor();
        }
        if (GP1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1) {
            PollenMotor.setVelocity(ticksToVelocity(2750,1));
        } else {
            PollenMotor.stopMotor();
        }
    }
    @Override
    public void stop(){
    }

    public double motorVoltage(double volts){
        return volts/voltageSensor.getVoltage();
    }
    public void drive(){
        mecanumDrive.driveRobotCentric(GP1.getRightX(), -GP1.getRightY(), GP1.getLeftX(), true);
        if (GP1.getRightX() != 0) {
            if (GP1.getRightX() / Math.abs(GP1.getRightX()) != GP1Old.getRightX() / Math.abs(GP1Old.getRightX())) {
                mecanumDrive.stop();
            }
        }
        if (GP1.getRightY() != 0) {
            if (GP1.getRightY() / Math.abs(GP1.getRightY()) != GP1Old.getRightY() / Math.abs(GP1Old.getRightY())) {
                mecanumDrive.stop();
            }
        }
        if (GP1.getLeftX() != 0) {
            if (GP1.getLeftX() / Math.abs(GP1.getLeftX()) != GP1Old.getLeftX() / Math.abs(GP1Old.getLeftX())) {
                mecanumDrive.stop();
            }
        }
    }
    public double ticksToVelocity(double RPM, double gearRatio) {
        return RPM * gearRatio * 28 / 60 ;
    }
    public void logger(){
        Logger.recordOutput("Time", getRuntime());
        Logger.recordOutput("Intake RPM", ticksToVelocity(IntakeMotor.getVelocity(), 5));
        Logger.recordOutput("Pollen RPM", ticksToVelocity(PollenMotor.getVelocity(), 1));
    }
}
