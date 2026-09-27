package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.Robot.Subsystem.Drivetrain;

@TeleOp(name = "TeleOp", group = "TeleOp")
public class Teleop extends OpMode {
    final Robot Robot = new Robot(this);

    @Override
    public void init() {
        Robot.init();
    }

    @Override
    public void loop() {
        Robot.pinpoint.update();
        GamepadEx gp1 = new GamepadEx(gamepad1);
        GamepadEx gp2 = new GamepadEx(gamepad2);
    }
}
