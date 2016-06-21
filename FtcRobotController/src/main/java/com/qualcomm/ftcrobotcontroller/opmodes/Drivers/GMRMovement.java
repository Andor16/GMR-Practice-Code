package com.qualcomm.ftcrobotcontroller.opmodes.Drivers;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robocol.Telemetry;

/**
 * Created by Payton on 6/14/2016.
 * Shorthand for moving the robot forwards and backwards, or turning.
 */
public class GMRMovement {

    private DcMotor leftDrive;
    private DcMotor rightDrive;

    private AHRS gyro;

    private String interruptionMessage;

    private Telemetry tele;

    /**
     *
     * @param hm The HardwareMap input
     *           Generates a new GMRMovement object, only requires HardwareMap as an input.
     */
    public GMRMovement(HardwareMap hm, Telemetry tm) {
        tm.addData("", "Setting up the motors");
        tele = tm;
        leftDrive = hm.dcMotor.get("leftDrive");
        rightDrive = hm.dcMotor.get("rightDrive");
        int NAVX_DIM_I2C_PORT = 3;
        gyro = AHRS.getInstance(hm.deviceInterfaceModule.get("DIM1"),
                NAVX_DIM_I2C_PORT,
                AHRS.DeviceDataType.kProcessedData);
        interruptionMessage = "You put a number over 180 into turnDegrees";
        tele.addData("Done setting up the object", "");
    }

    /**
     *
     * @param dir Specifies the direction for the movement
     * @param power Specifies how much power the motors will move with
     *              The main method, allows you to move the robot forward, backward, right turn, or left turn, and also how much power to use
     */
    public void move (Direction dir, double power) {

        if (dir == Direction.Forward) {
            tele.addData("Motor Power", power/100d);
            leftDrive.setPower((-power)/100d);
            rightDrive.setPower((power)/100d);
        } else if (dir == Direction.Backward) {
            leftDrive.setPower((power)/100d);
            rightDrive.setPower((-power)/100d);
        } else if (dir == Direction.Right) {
            leftDrive.setPower((-power)/100d);
            rightDrive.setPower((-power)/100d);
        } else if (dir == Direction.Left) {
            leftDrive.setPower((power)/100d);
            rightDrive.setPower((power)/100d);
        }

    }

    /**
     * @param degrees Can only go up to 180, and can not be negative.
     * @param dir Specifies the direction to turn, can only be Right or Left
     * @throws IllegalArgumentException Throws an exception when degrees is greater than 180 or direction in backwards
     * Turns a specific number of degrees left or right, but only up to 180 degrees.
     */
    public void turn (Direction dir, int degrees) {
        gyro.zeroYaw();
        if (degrees > 180) {
            throw new IllegalArgumentException(interruptionMessage);
        }
        if (dir == Direction.Right) {
            while (gyro.getYaw() < degrees || gyro.getYaw() < 0) {
                tele.addData("Current Gyroscope position", gyro.getYaw());
                move(Direction.Right, 50);
                tele.addData("Current Gyro Heading", gyro.getYaw());
            }
            stop();
        } else if (dir == Direction.Left) {
            while (-gyro.getYaw() < degrees) {
                move(Direction.Left, 50);
                tele.addData("Current Gyro Heading", gyro.getYaw());
            }
            stop();
        } else if (dir == Direction.Backward) {
            throw new IllegalArgumentException("Incorrect direction input.");
        }

    }

    /**
     * Turns 90 degrees left or right, or right 180 degrees
     * @param dir Specifies the direction to turn, Left, Right, or Backwards (180 degrees to the right).
     */
    public void turn(Direction dir) {
        gyro.zeroYaw();
        if (dir == Direction.Right) {
            turn(Direction.Right, 90);
        } else if (dir == Direction.Left) {
            turn(Direction.Left, 90);
        } else if (dir == Direction.Backward) {
            turn(Direction.Right, 179);
        }
    }

    /**
     * Stops the movement of the motors
     */
    public void stop() {
        leftDrive.setPower(0);
        rightDrive.setPower(0);
    }

    /**
     * Returns either Roll, Pitch, or Yaw from the gyroscope
     * @param dir Determines what the method sends back
     * @return Returns a double based on either Roll, Pitch, or Yaw
     */
    public float showGyro(Direction dir) {
        if (dir == Direction.Pitch) {
            return gyro.getPitch();
        } else if (dir == Direction.Roll) {
            return gyro.getRoll();
        } else if (dir == Direction.Yaw) {
            return gyro.getYaw();
        } else {
            return 0;
        }
    }



    public enum Direction {Left, Right, Forward, Backward, Yaw, Pitch, Roll}
}
