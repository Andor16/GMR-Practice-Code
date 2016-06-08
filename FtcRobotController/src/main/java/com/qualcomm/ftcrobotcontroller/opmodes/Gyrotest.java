package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.robocol.Telemetry;

/**
 * Created by Payton on 6/8/2016
 */
public class Gyrotest extends LinearOpMode {
    Telemetry tele =  new Telemetry();
    GMRGyroTurn turn;

    @Override
    public void runOpMode() throws InterruptedException {
        turn = new GMRGyroTurn(hardwareMap);
        waitForStart();
        turn.turn(Direction.Right);
//        sleep(500);
//        turn.turn(Direction.Left, 135);
//        sleep(500);
//        turn.turn(Direction.Left);
//        sleep(500);
//        turn.turn(Direction.Right);
//        sleep(500);
//        turn.turn(Direction.backward);
//        sleep(500);
//        turn.turn(Direction.Right, 360);
    }
}
