package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

//runs on the computer, not the robot. pick the "MeepMeep" run config at the top of android studio and hit run
//paths here use the same builder as the real auton (drive.actionBuilder) so you can copy them straight over
public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                //keep these the same as MecanumDrive.PARAMS: maxWheelVel, maxProfileAccel, maxAngVel, maxAngAccel, track width (in)
                .setConstraints(50, 50, Math.PI, Math.PI, 15)
                //robot size in inches (width, length), change to ours
                .setDimensions(18, 18)
                .build();

        //example path, replace w our auton
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(0, 0, 0))
                .lineToX(30)
                .turn(Math.toRadians(90))
                .lineToY(30)
                .turn(Math.toRadians(90))
                .lineToX(0)
                .turn(Math.toRadians(90))
                .lineToY(0)
                .turn(Math.toRadians(90))
                .build());

        //no field image for this years game yet so just a grid, coords still line up
        meepMeep.setBackground(MeepMeep.Background.GRID_GRAY)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
