package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TeleOp", group = "TeleOp")
public class Teleop extends OpMode {
    Robot Robot = new Robot(this);

    @Override
    public void init() {
        Robot.init();
    }

    @Override
    public void loop() {
        Robot.pinpoint.update();
    }
}
