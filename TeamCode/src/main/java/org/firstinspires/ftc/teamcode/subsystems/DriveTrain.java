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

    //telemetry variable for displaying motor power
    private Telemetry telemetry;


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

        frontLeft.setPower((drive + strafe + rotation)*speed);
        frontRight.setPower((drive - strafe + rotation)*speed);
        backLeft.setPower((drive - strafe - rotation)*speed);
        backRight.setPower((drive + strafe - rotation)*speed);

    }


    //Function for auton when voltage is key
    public void MecanumDriveVoltage(double drive, double strafe, double rotation, double speed, double volt){

        frontLeft.setPower((drive + strafe + rotation)*speed*volt);

        frontRight.setPower((drive - strafe + rotation)*speed*volt);

        backLeft.setPower((drive - strafe - rotation)*speed*volt);

        backRight.setPower((drive + strafe - rotation)*speed*volt);

    }


    //displays the telemetry data for teleop when testing
    public void Telemetry(){

        telemetry.addData("Front Left Power", frontLeft.getPower());
        telemetry.addData("Front Right Power", frontRight.getPower());
        telemetry.addData("Back Left Power", backLeft.getPower());
        telemetry.addData("Back Right Power", backRight.getPower());

    }
}