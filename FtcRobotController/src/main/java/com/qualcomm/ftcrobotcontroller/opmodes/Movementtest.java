package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.opmodes.Drivers.GMRMovement.Direction;
import com.qualcomm.ftcrobotcontroller.opmodes.Drivers.GMRMovement;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Payton on 6/8/2016
 */
public class Movementtest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        GMRMovement move = new GMRMovement(hardwareMap, telemetry);
        telemetry.addData("Beginning of the program.", "");
        waitForStart();
        move.move(Direction.Forward, 65);
        sleep(500);
        move.stop();
        waitForStart();
        move.turn(Direction.Right);
        sleep(500);
        move.turn(Direction.Left);
        sleep(500);
        move.turn(Direction.Right, 45);
        sleep(500);
        move.turn(Direction.Left, 45);
        sleep(500);
        move.turn(Direction.Backward);
        sleep(500);
        move.turn(Direction.Right, 360);
    }
}
