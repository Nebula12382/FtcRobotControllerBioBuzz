//imports modules for later use
package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.HardwareMap.*;


public class DriveTrain{

    //inits the DCmotor variables for later use
    private DcMotor frontLeft, frontRight, backLeft, backRight;

    private static final double NOMINAL_VOLTAGE = 12.0;


    //Maps the DC motors from the driverstation and the control hub
    public void HardwareMapSubsystem(HardwareMap hardwareMap){

        frontLeft = hardwareMap.get(DcMotor.class,"fl");
        frontRight = hardwareMap.get(DcMotor.class, "fr");
        backLeft = hardwareMap.get(DcMotor.class,"bl");
        backRight = hardwareMap.get(DcMotor.class,"br");

        //sets the behavior when there is 0 power to brake
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }




    public double GetVolt(HardwareMap hardwareMap) {
        double lowest = Double.MAX_VALUE;

        for (VoltageSensor sensor : hardwareMap.voltageSensor) {

            double voltage = sensor.getVoltage();

            if (voltage > 0 && voltage < lowest) {
                lowest = voltage;
            }

        }
        return ((lowest== Double.MAX_VALUE) ? 12.0: lowest);
    }





    //Used for teleop to make sure the driver can trottle it's own power
    public void MecanumDrive(double drive, double strafe, double rotation, double speed){

        //scales all powers down together so none go past 1, which the SDK would clip and throw off the turning
        double max = Math.max(Math.abs(drive) + Math.abs(strafe) + Math.abs(rotation), 1);

        frontLeft.setPower((drive + strafe + rotation) / max * speed);
        frontRight.setPower((drive - strafe + rotation) / max * speed);
        backLeft.setPower((drive - strafe - rotation) / max * speed);
        backRight.setPower((drive + strafe - rotation) / max * speed);

    }


    //Function for auton when voltage is key
    //volt is the battery voltage from GetVolt(); a lower battery gets slightly more power so the robot moves the same
    public void MecanumDriveVoltage(double drive, double strafe, double rotation, double speed, double volt){

        MecanumDrive(drive, strafe, rotation, speed * NOMINAL_VOLTAGE / volt);

    }


    //displays the telemetry data for teleop when testing
    public void Telemetry(Telemetry telemetry){

        telemetry.addData("Front Left Power", frontLeft.getPower());
        telemetry.addData("Front Right Power", frontRight.getPower());
        telemetry.addData("Back Left Power", backLeft.getPower());
        telemetry.addData("Back Right Power", backRight.getPower());

    }
}