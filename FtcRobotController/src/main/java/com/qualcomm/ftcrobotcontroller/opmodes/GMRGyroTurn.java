package com.qualcomm.ftcrobotcontroller.opmodes;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robocol.Telemetry;

/**
 * Created by Payton on 6/8/2016
 */
public class GMRGyroTurn {

    private DcMotor leftDrive;
    private DcMotor rightDrive;

    private AHRS gyro;

    private String interruptionMessage;

    Telemetry tele = new Telemetry();

    /**
     *
     * @param hp Only required input for this object
     */
    public GMRGyroTurn(HardwareMap hp) {
        tele.addData("Setting up the Object","");
        leftDrive = hp.dcMotor.get("leftDrive");
        rightDrive = hp.dcMotor.get("rightDrive");
        int NAVX_DIM_I2C_PORT = 3;
        gyro = AHRS.getInstance(hp.deviceInterfaceModule.get("DIM1"),
                NAVX_DIM_I2C_PORT,
                AHRS.DeviceDataType.kProcessedData);
        interruptionMessage = "You put a number over 180 into turnDegrees";
        tele.addData("Done setting up the object","");
    }

    /**
     * @param degrees Can only go up to 180, and can not be negative.
     */
    public void turn (Direction dir, int degrees) {
        gyro.zeroYaw();
        if (dir == Direction.Right) {
            if (degrees < 180) {
                while (gyro.getYaw() < degrees) {
                    leftDrive.setPower(-0.5);
                    rightDrive.setPower(-0.5);
                    tele.addData("Current Gyro Heading", gyro.getYaw());
                }
                leftDrive.setPower(0);
                rightDrive.setPower(0);
            } else if (degrees > 180) {
                throw new IllegalArgumentException(interruptionMessage);
            }
        } else if (dir == Direction.Left) {
            if (degrees < 180) {
                while (-gyro.getYaw() < degrees) {
                    leftDrive.setPower(0.5);
                    rightDrive.setPower(0.5);
                    tele.addData("Current Gyro Heading", gyro.getYaw());
                }
                leftDrive.setPower(0);
                rightDrive.setPower(0);
            } else if (degrees > 180) {
                throw new IllegalArgumentException(interruptionMessage);
            }
        }

    }

    /**
     *
     * @param dir input direction to turn.
     */
    public void turn(Direction dir) {
        gyro.zeroYaw();
        if (dir == Direction.Right) {
            while (gyro.getYaw() < 90) {
                leftDrive.setPower(-0.5);
                rightDrive.setPower(-0.5);
                tele.addData("Current Gyro Heading", gyro.getYaw());
            }
            leftDrive.setPower(0);
            rightDrive.setPower(0);
        } else if (dir == Direction.Left) {
            while (-gyro.getYaw() < 90) {
                leftDrive.setPower(0.5);
                rightDrive.setPower(0.5);
                tele.addData("Current Gyro Heading", gyro.getYaw());
            }
            leftDrive.setPower(0);
            rightDrive.setPower(0);
        } else if (dir == Direction.backward) {
            while (gyro.getYaw() < 180) {
                leftDrive.setPower(-0.5);
                rightDrive.setPower(-0.5);
                tele.addData("Current Gyro Heading", gyro.getYaw());
            }
            leftDrive.setPower(0);
            rightDrive.setPower(0);
        }
    }
}

enum Direction {Left,Right,backward}