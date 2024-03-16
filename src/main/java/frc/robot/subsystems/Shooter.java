package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.revrobotics.*;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Shooter extends SubsystemBase {
    CANSparkMax m_topShooterMotor = new CANSparkMax(Constants.ShooterConstants.topShooterMotorID, MotorType.kBrushless);
    CANSparkMax m_bottomShooterMotor = new CANSparkMax(Constants.ShooterConstants.bottomShooterMotorID, MotorType.kBrushless);
    

    private SparkPIDController m_BottomShooterPIDController, m_TopShooterPidController;
    public double s_kP, s_kI, s_kD, s_kFF, supp_ShooterValueTop,supp_ShooterValueBottom,topVal,bottomVal;
    public int velo, topVelo, bottomVelo;

    public Shooter() {
        s_kP = Constants.ShooterConstants.shooter_P;
        s_kI = Constants.ShooterConstants.shooter_I;
        s_kD = Constants.ShooterConstants.shooter_D;
        s_kFF = Constants.ShooterConstants.shooter_FF;

        supp_ShooterValueTop = Constants.ShooterConstants.top_shooterVelo;
        supp_ShooterValueBottom = Constants.ShooterConstants.bottom_shooterVelo;

        m_bottomShooterMotor.restoreFactoryDefaults();
        m_topShooterMotor.restoreFactoryDefaults();
        

        m_BottomShooterPIDController = m_bottomShooterMotor.getPIDController();
        m_TopShooterPidController = m_topShooterMotor.getPIDController();
        

        m_BottomShooterPIDController.setP(s_kP);
        m_TopShooterPidController.setP(s_kP);
    

        m_BottomShooterPIDController.setI(s_kI);
        m_TopShooterPidController.setI(s_kI);
     

        m_BottomShooterPIDController.setD(s_kD);
        m_TopShooterPidController.setD(s_kD);
     

        m_BottomShooterPIDController.setFF(s_kFF);
        m_TopShooterPidController.setFF(s_kFF);
       

        m_BottomShooterPIDController.setOutputRange(Constants.minMaxOutputConstants.kMinOutput,Constants.minMaxOutputConstants.kMaxOutput);
        m_TopShooterPidController.setOutputRange(Constants.minMaxOutputConstants.kMinOutput,Constants.minMaxOutputConstants.kMaxOutput);

        SmartDashboard.putNumber("Top Shooter Velo - AMP",supp_ShooterValueTop);
        SmartDashboard.putNumber("Bottom Shooter Velo - AMP",supp_ShooterValueBottom);
    }

    public void periodic(){
        //Output the velocity of the shooter motors to the dashboard
        SmartDashboard.putNumber("Top Shooter Motor Velocity:",m_topShooterMotor.getEncoder().getVelocity());
        SmartDashboard.putNumber("Bottom Shooter Motor Velocity:",m_bottomShooterMotor.getEncoder().getVelocity());

        topVal = SmartDashboard.getNumber("Top Shooter Velo - AMP",supp_ShooterValueTop);
        bottomVal = SmartDashboard.getNumber("Bottom Shooter Velo - AMP",supp_ShooterValueBottom);

        if(topVal==0.0){
            topVal = supp_ShooterValueTop;
        }
        if(bottomVal==0.0){
            bottomVal = supp_ShooterValueBottom;
        }


    }


    public void shootSpeaker(int Velo){
        //set the velocity of the motor based on input value
        m_BottomShooterPIDController.setReference(Velo,CANSparkBase.ControlType.kVelocity);
        m_TopShooterPidController.setReference(Velo,CANSparkBase.ControlType.kVelocity);
    }

    public void shootAmp(int topVelo, int bottomVelo){
        //set the velocity of the motor based on input value
        m_BottomShooterPIDController.setReference(bottomVelo,CANSparkBase.ControlType.kVelocity);
        m_TopShooterPidController.setReference(topVelo,CANSparkBase.ControlType.kVelocity);
        
    }

    public void shootREV(int revVelo){
        //set the velocity of the motor based on input value
        m_BottomShooterPIDController.setReference(revVelo,CANSparkBase.ControlType.kVelocity);
        m_TopShooterPidController.setReference(revVelo,CANSparkBase.ControlType.kVelocity);
    }
    
    public void shootAmpSD(){
        m_BottomShooterPIDController.setReference(bottomVal, CANSparkBase.ControlType.kVelocity);
        m_TopShooterPidController.setReference(topVal, CANSparkBase.ControlType.kVelocity);
    }
    
    
    public void stop(){
        //stop the motor
        m_BottomShooterPIDController.setReference(0,CANSparkBase.ControlType.kVelocity);
        m_TopShooterPidController.setReference(0,CANSparkBase.ControlType.kVelocity);
       m_topShooterMotor.setVoltage(0);
       m_bottomShooterMotor.setVoltage(0);
    }
    
}
