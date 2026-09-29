package org.firstinspires.ftc.teamcode.teleop;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.ColorDetector;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;
import org.firstinspires.ftc.teamcode.subsystems.Intake;


@TeleOp(name = "Nebula Teleop", group = "TeleOp")
public class NebulaTeleop extends LinearOpMode{
    private final DriveTrain driveTrain = new DriveTrain();
    private final Outtake outTake = new Outtake();
    private final Intake intake = new Intake();
    private final ColorDetector colorDetector = new ColorDetector();

    private static final ColorDetector.DetectedColor OPPONENT_COLOR = ColorDetector.DetectedColor.BLUE;
    private static final double EJECT_TIME = 0.5;
    private final ElapsedTime ejectTimer = new ElapsedTime();
    private boolean ejecting = false;

    @Override
    public void runOpMode() throws InterruptedException{
        driveTrain.HardwareMapSubsystem(hardwareMap);
        outTake.HardwareMapSubsystem(hardwareMap);
        intake.HardwareMapSubsystem(hardwareMap);
        colorDetector.HardwareMapSubsystem(hardwareMap);

        waitForStart();
        while (opModeIsActive()){
            double drive = gamepad1.left_stick_x;
            double strafe = -gamepad1.left_stick_y;
            double rotation = gamepad1.right_stick_x;


            if (colorDetector.readColor() == OPPONENT_COLOR){
                ejecting = true;
                ejectTimer.reset();
            }

            if (ejecting && ejectTimer.seconds() < EJECT_TIME){
                intake.out();
            }
            else{
                ejecting = false;
                if (gamepad1.right_trigger_pressed){
                    intake.in();
                }
                else if (gamepad1.left_trigger_pressed){
                    intake.out();
                }
                else{
                    intake.stop();
                }
            }


            driveTrain.MecanumDrive(drive,strafe,rotation, gamepad1.left_stick_button ? 0.15 : 1);
            intake.update_telemetry();
            colorDetector.update_telemetry(telemetry);
            driveTrain.Telemetry();
            telemetry.update();
        }
    }
}
