package qubexplorer.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.util.NbPreferences;
import org.openide.windows.WindowManager;
import qubexplorer.server.SonarQube;
import qubexplorer.ui.options.SonarQubeOptionsPanel;

@ActionID(
        category = "Build",
        id = "qubexplorer.ui.SonarDialogAction2")
@ActionRegistration(
        displayName = "#CTL_SonarDialogAction2")
@Messages("CTL_SonarDialogAction2=Get Issues from Server ...")
@ActionReferences(value={
@ActionReference(path="Projects/Actions", position = 8963, separatorBefore = 8956, separatorAfter = 8968),
@ActionReference(path = "Menu/Source", position = 8963, separatorBefore = 8956, separatorAfter = 8968)})
public final class CustomServerIssuesAction implements ActionListener {

    private final Project context;

    public CustomServerIssuesAction(Project context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        ProjectChooser chooser=new ProjectChooser(WindowManager.getDefault().getMainWindow(), true);
        chooser.setSelectedUrl(NbPreferences.forModule(SonarQubeOptionsPanel.class).get("address", "http://localhost:9000"));
        if(chooser.showDialog() == ProjectChooser.Option.ACCEPT) {
            SummaryWorker worker=new SummaryWorker(new SonarQube(chooser.getSelectedUrl()), context, chooser.getSelectedProjectKey());
            worker.setTriggerActionPlans(true);
            worker.execute();
        }
    }
    
}