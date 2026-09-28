package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.seattlesolvers.solverslib.drivebase.MecanumDrive;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

import java.util.Arrays;

@SuppressWarnings("FieldCanBeLocal")
public class Robot {
    private OpMode OpMode; // gain access to methods in the calling OpMode.
    private HardwareMap HardwareMap = null;
    private Telemetry Telemetry = null;

    // Define Motor and Servo objects
    private MotorEx fL = null;
    private MotorEx bL = null;
    private MotorEx fR = null;
    private MotorEx bR = null;
    private MotorEx pL = null;
    private ServoEx pLD = null;
    private ServoEx pLH = null;
    private MotorEx nL = null;
    private ServoEx nLD = null;
    private ServoEx nLH = null;
    private MotorEx iM = null;
    private ServoEx iB = null;
    public GoBildaPinpointDriver pinpoint = null;

    private final int HDHexBaseSpeed = 6000;
    private final double DriveGearRatio = (1 / 5.0);
    @SuppressWarnings("PointlessArithmeticExpression")
    private final double LauncherGearRatio = (1 / 1.0);
    private final double IntakeGearRatio = (1 / 5.0);

    private MecanumDrive mecanumDrive = null;

    // Define a constructor that allows the OpMode to pass a reference to itself.
    public Robot(LinearOpMode opmode) {
        OpMode = opmode;
    }

    public Robot(OpMode opmode) {
        OpMode = opmode;
    }

    public void init() {
        HardwareMap = OpMode.hardwareMap;
        Telemetry = OpMode.telemetry;

        // Define and Initialize Motors (note: need to use reference to actual OpMode).
        fL = new MotorEx(HardwareMap, "frontLeft", 28, HDHexBaseSpeed * DriveGearRatio);
        fL.setRunMode(Motor.RunMode.RawPower);
        fL.setInverted(false);
        fL.resetEncoder();

        bL = new MotorEx(HardwareMap, "backLeft", 28, HDHexBaseSpeed * DriveGearRatio);
        bL.setRunMode(Motor.RunMode.RawPower);
        bL.setInverted(false);
        bL.resetEncoder();

        fR = new MotorEx(HardwareMap, "frontRight", 28, HDHexBaseSpeed * DriveGearRatio);
        fR.setRunMode(Motor.RunMode.RawPower);
        fR.setInverted(false);
        fR.resetEncoder();

        bR = new MotorEx(HardwareMap, "backRight", 28, HDHexBaseSpeed * DriveGearRatio);
        bR.setRunMode(Motor.RunMode.RawPower);
        bR.setInverted(false);
        bR.resetEncoder();

        pL = new MotorEx(HardwareMap, "pollenLauncher", 28, HDHexBaseSpeed * LauncherGearRatio);
        pL.setRunMode(MotorEx.RunMode.VelocityControl);
        pL.setInverted(false);
        pL.resetEncoder();

        pLD = new ServoEx(HardwareMap, "pollenLauncherDoor");
        pLD.setInverted(false);

        pLH = new ServoEx(HardwareMap, "pollenLauncherHood");
        pLH.setInverted(false);

        nL = new MotorEx(HardwareMap, "nectarLauncher", 28, HDHexBaseSpeed * LauncherGearRatio);
        nL.setRunMode(Motor.RunMode.VelocityControl);
        nL.setInverted(false);
        nL.resetEncoder();

        nLD = new ServoEx(HardwareMap, "nectarLauncherDoor");
        nLD.setInverted(false);

        nLH = new ServoEx(HardwareMap, "nectarLauncherHood");
        nLH.setInverted(false);

        iM = new MotorEx(HardwareMap, "intakeMotor", 28, HDHexBaseSpeed * IntakeGearRatio);
        iM.setRunMode(Motor.RunMode.RawPower);
        iM.setInverted(false);
        iM.resetEncoder();

        iB = new ServoEx(HardwareMap, "intakeBar");
        iB.setInverted(false);

        pinpoint = HardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.resetPosAndIMU();
        pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0));

        mecanumDrive = new MecanumDrive(fL, fR, bL, bR);

        Telemetry.addData(">", "Hardware Initialized");
        Telemetry.update();
    }

    public class Subsystem {

        public class Drivetrain {
            public void Field(double strafe, double forward, double turn) {
                mecanumDrive.driveFieldCentric(strafe, forward, turn, pinpoint.getHeading(AngleUnit.DEGREES));
            }
            public void Robot(double strafe, double forward, double turn) {
                mecanumDrive.driveRobotCentric(strafe, forward, turn);
            }
        }

        public class Intake {

        }

        public class NectarLauncher {
            private final Utils.BilinearLUT nectarAngleLut = new Utils.BilinearLUT(
                    new double[]{0.0, 10.0, 20.0}, //X-Values
                    new double[]{0.0, 5.0, 10.0},  //Y-Values
                    new double[][]{                //Output Values
                            {0.0, 5.0, 10.0},  //1st x
                            {10.0, 15.0, 20.0},  //2nd x
                            {20.0, 25.0, 30.0}});//3rd x
            //more rows are more y's, more columns are more x's

            public void target() {
                //if inBounds
                double Theta = nectarAngleLut.interpolate(pinpoint.getPosX(DistanceUnit.INCH), pinpoint.getPosY(DistanceUnit.INCH));
                nLH.set(Theta);
                //else
                //aim towards center
            }
        }

        public class PollenLauncher {
            private final Utils.BilinearLUT pollenAngleLut = new Utils.BilinearLUT(
                    new double[]{0.0, 10.0, 20.0}, //X-Values
                    new double[]{0.0, 5.0, 10.0},  //Y-Values
                    new double[][]{                //Output Values
                            {0.0, 5.0, 10.0},  //1st x
                            {10.0, 15.0, 20.0},  //2nd x
                            {20.0, 25.0, 30.0}});//3rd x
            //more rows are more y's, more columns are more x's

            public void target() {
                //if inBounds
                double Theta = pollenAngleLut.interpolate(pinpoint.getPosX(DistanceUnit.INCH), pinpoint.getPosY(DistanceUnit.INCH));
                pLH.set(Theta); //all of this is temporary and not an actual equation
                //else
                //aim towards center
            }
        }
    }



    private static class Utils {

        /**
         * 2D Bilinear Interpolation Lookup Table (LUT).
         * Interpolates values across non-uniform or uniform grid points.
         */
        private static class BilinearLUT {
            private final double[] xGrid;
            private final double[] yGrid;
            private final double[][] table; // table[xIndex][yIndex]

            public BilinearLUT(double[] xGrid, double[] yGrid, double[][] table) {
                if (xGrid.length < 2 || yGrid.length < 2) {
                    throw new IllegalArgumentException("Grids must contain at least 2 points.");
                }
                if (table.length != xGrid.length || table[0].length != yGrid.length) {
                    throw new IllegalArgumentException("Table dimensions must match grid lengths.");
                }
                this.xGrid = xGrid;
                this.yGrid = yGrid;
                this.table = table;
            }

            /**
             * Interpolates value at query coordinates (x, y).
             * Out-of-bounds queries are clamped to grid boundaries.
             */
            public double interpolate(double x, double y) {
                // Clamp inputs to grid boundaries
                double xClamped = Math.max(xGrid[0], Math.min(x, xGrid[xGrid.length - 1]));
                double yClamped = Math.max(yGrid[0], Math.min(y, yGrid[yGrid.length - 1]));

                // Find cell indices
                int i = findInterval(xGrid, xClamped);
                int j = findInterval(yGrid, yClamped);

                double x1 = xGrid[i];
                double x2 = xGrid[i + 1];
                double y1 = yGrid[j];
                double y2 = yGrid[j + 1];

                // Corner values: Q11 = (x1, y1), Q21 = (x2, y1), Q12 = (x1, y2), Q22 = (x2, y2)
                double q11 = table[i][j];
                double q21 = table[i + 1][j];
                double q12 = table[i][j + 1];
                double q22 = table[i + 1][j + 1];

                // Normalized fractions in range [0, 1]
                double t = (xClamped - x1) / (x2 - x1);
                double u = (yClamped - y1) / (y2 - y1);

                // Bilinear interpolation formula
                return (1 - t) * (1 - u) * q11
                        + t * (1 - u) * q21
                        + (1 - t) * u * q12
                        + t * u * q22;
            }

            private int findInterval(double[] grid, double value) {
                int idx = Arrays.binarySearch(grid, value);
                if (idx >= 0) {
                    return Math.min(idx, grid.length - 2);
                }
                int insertionPoint = -idx - 1;
                return Math.min(Math.max(0, insertionPoint - 1), grid.length - 2);
            }
        }
    }
}