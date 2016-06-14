package com.qualcomm.ftcrobotcontroller.opmodes.Drivers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robocol.Telemetry;

/**
 * Created by Payton on 6/14/2016.
 * Shorthand for moving the robot forwards and backwards, or turning.
 */
public class GMRMotor {

    private DcMotor leftDrive;
    private DcMotor rightDrive;

    private Telemetry tele;

    /**
     *
     * @param hm The HardwareMap input
     *           Generates a new GMRMotor object, only requires HardwareMap as an input.
     */
    public GMRMotor (HardwareMap hm, Telemetry tm) {
        tm.addData("", "Setting up the motors");
        tele = tm;
        leftDrive = hm.dcMotor.get("leftDrive");
        rightDrive = hm.dcMotor.get("rightDrive");
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
     * Stops the movement of the motors
     */
    public void stop () {
        leftDrive.setPower(0);
        rightDrive.setPower(0);
    }
    public enum Direction {Left, Right, Forward, Backward}
}
