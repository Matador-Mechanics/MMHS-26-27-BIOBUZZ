package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.qualcomm.hardware.motors.RevRoboticsUltraPlanetaryHdHexMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@TeleOp(name = "VisionTesting", group = "Testing")
public class LauncherDecelerationTest extends OpMode {
    File log;
    FileWriter myWriter;
    DcMotorEx launcherMotor;
    String filename = filename();

    @Override
    public void init() {
        launcherMotor = hardwareMap.get(DcMotorEx.class, "launcherMotor");
        launcherMotor.setMotorType(MotorConfigurationType.getMotorType(RevRoboticsUltraPlanetaryHdHexMotor.class));
        launcherMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launcherMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        launcherMotor.setPower(1);

        try {
            log = new File(filename); // Create File object
            if (log.createNewFile()) {  // Try to create the file
                System.out.println("File created: " + log.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace(); // Print error details
        }

        try {
            myWriter = new FileWriter(filename);
            myWriter.write("Log Start: ["); // Header
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        try (FileWriter myWriter = new FileWriter(filename, true)) {
            myWriter.write(data()); // Append velocity to file
            System.out.println("Successfully appended to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        launcherMotor.setPower(0);
    }

    @Override
    public void loop() {
        try (FileWriter myWriter = new FileWriter(filename, true)) {
            myWriter.write(data()); // Append velocity to file
            System.out.println("Successfully appended to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try (FileWriter myWriter = new FileWriter(filename, true)) {
            myWriter.write("\n] :Log End"); // Append velocity to file
            System.out.println("Successfully appended to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String filename() { // Creates the file name
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US).format(new Date());
        String opModeName = this.toString();
        return timeStamp + "_" + opModeName + ".txt";
    }
    private String data() {
        return "\n[" + launcherMotor.getVelocity() + ", " + System.nanoTime() + "]"; // getVelocity returns values of ticks per second and nanoTime should be able to provide timing offsets
    }
}
