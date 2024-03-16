package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;

public class RunIndexer extends Command{
    private Indexer s_Indexer;
    private int c_velocity;
    private int c_velocityaux;

    public RunIndexer(Indexer s_Indexer, int c_velocity, int c_velocityaux){
        this.s_Indexer = s_Indexer;
        this.c_velocity = c_velocity;
        this.c_velocityaux = c_velocityaux;
        addRequirements(s_Indexer);
    }
    
    //public RunIndexer(Indexer s_Indexer2, int indexvelo, int indexveloaux) {
        //TODO Auto-generated constructor stub
    //}
    

    public void execute(){
        //Set the shooter to the desired speed
        s_Indexer.runIndex(c_velocity, c_velocityaux);
    }
    public void isInterrupted(){
        //Stop shooter if command is interrupted
        s_Indexer.runIndex(0,0);
        s_Indexer.stop();
    }
    public void end(){
        //stop shooter when command ends
        s_Indexer.runIndex(0,0);
    }
    public boolean isFinished(){
        return s_Indexer.getphotoswitch();
    }
}
