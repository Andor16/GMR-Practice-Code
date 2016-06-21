package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.opmodes.Drivers.GMRMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Payton on 6/8/2016
 */
public class Movementtest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        GMRGyroTurn turn = new GMRGyroTurn(hardwareMap);
        GMRMotor move = new GMRMotor(hardwareMap, telemetry);
        telemetry.addData("Beginning of the program.", "");
        waitForStart();
        move.move(GMRMotor.Direction.Forward, 65);
        sleep(500);
        move.stop();
        turn = new GMRGyroTurn(hardwareMap);
        waitForStart();
        turn.turn(GMRGyroTurn.Direction.Right);
        sleep(500);
        turn.turn(GMRGyroTurn.Direction.Left);
        sleep(500);
        turn.turn(GMRGyroTurn.Direction.Right, 45);
        sleep(500);
        turn.turn(GMRGyroTurn.Direction.Left, 45);
        sleep(500);
        turn.turn(GMRGyroTurn.Direction.Backward);
        sleep(500);
        turn.turn(GMRGyroTurn.Direction.Right, 360);
    }
}
