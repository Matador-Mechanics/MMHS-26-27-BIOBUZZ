package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

@TeleOp(name = "TeleOp", group = "TeleOp")
public class Teleop extends OpMode {

    @Override
    public void init() {

    }

    @Override
    public void loop() {

        GamepadEx gp1 = new GamepadEx(gamepad1);
        GamepadEx gp2 = new GamepadEx(gamepad2);
    }
}
