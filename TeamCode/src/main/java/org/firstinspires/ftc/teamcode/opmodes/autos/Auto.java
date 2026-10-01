package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.CommandBase.Robot;

@Autonomous(name = "Auto", group = "Auto")
public class Auto extends LinearOpMode {
    final Robot Robot = new Robot(this);

    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init();

        waitForStart();
    }
}
