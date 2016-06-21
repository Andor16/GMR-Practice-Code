package com.qualcomm.ftcrobotcontroller.opmodes;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.ftcrobotcontroller.opmodes.Drivers.GMRMotor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robocol.Telemetry;

/**
 * Created by Payton on 6/8/2016
 *
 * A class that makes turning with the gyroscope much easier, only requires HardwareMap as an input.
 * It can be used for making precise turns, and also has shorthand for when a 90 or 180 degree turn needs to be made.
 */
public class GMRGyroTurn {

    private DcMotor leftDrive;
    private DcMotor rightDrive;

    private AHRS gyro;

    private String interruptionMessage;

    private GMRMotor move;

    Telemetry tele = new Telemetry();

    /**
     *
     * @param hm The HardwareMap input
     *           Generates a new GMRGyroTurn object, only requires HardwareMap as an input.
     */
    public GMRGyroTurn(HardwareMap hm) {
        tele.addData("Setting up the Object", "");
        leftDrive = hm.dcMotor.get("leftDrive");
        rightDrive = hm.dcMotor.get("rightDrive");
        int NAVX_DIM_I2C_PORT = 3;
        gyro = AHRS.getInstance(hm.deviceInterfaceModule.get("DIM1"),
                NAVX_DIM_I2C_PORT,
                AHRS.DeviceDataType.kProcessedData);
        interruptionMessage = "You put a number over 180 into turnDegrees";
        tele.addData("Done setting up the object", "");
        move = new GMRMotor(hm, tele);
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
                    move.move(GMRMotor.Direction.Right, 50);
                    tele.addData("Current Gyro Heading", gyro.getYaw());
                }
                move.stop();
        } else if (dir == Direction.Left) {
                while (-gyro.getYaw() < degrees) {
                    move.move(GMRMotor.Direction.Left, 50);
                    tele.addData("Current Gyro Heading", gyro.getYaw());
                }
                move.stop();
        } else if (dir == Direction.Backward) {
            throw new IllegalArgumentException("Incorrect input direction.");
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
            turn(Direction.Right, 180);
        }
    }
    enum Direction {Left,Right, Backward}
}

