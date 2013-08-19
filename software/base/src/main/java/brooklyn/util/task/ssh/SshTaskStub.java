package brooklyn.util.task.ssh;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import brooklyn.location.basic.SshMachineLocation;
import brooklyn.util.collections.MutableMap;
import brooklyn.util.config.ConfigBag;
import brooklyn.util.text.Strings;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;

public class SshTaskStub {
    
    protected final List<String> commands = new ArrayList<String>();
    protected SshMachineLocation machine;
    
    // config data
    protected String summary;
    protected final ConfigBag config = ConfigBag.newInstance();
    
    public static enum ScriptReturnType { CUSTOM, EXIT_CODE, STDOUT_STRING, STDOUT_BYTES, STDERR_STRING, STDERR_BYTES }
    protected Function<SshTaskWrapper<?>, ?> returnResultTransformation = null;
    protected ScriptReturnType returnType = ScriptReturnType.EXIT_CODE;
    
    protected boolean runAsScript = false;
    protected boolean runAsRoot = false;
    protected boolean requireExitCodeZero = false;
    protected String extraErrorMessage = null;
    protected Map<String,String> shellEnvironment = new MutableMap<String, String>();

    public SshTaskStub() {}
    
    protected SshTaskStub(SshTaskStub source) {
        commands.addAll(source.commands);
        machine = source.getMachine();
        summary = source.getSummary();
        config.copy(source.config);
        returnResultTransformation = source.returnResultTransformation;
        returnType = source.returnType;
        runAsScript = source.runAsScript;
        runAsRoot = source.runAsRoot;
        requireExitCodeZero = source.requireExitCodeZero;
        extraErrorMessage = source.extraErrorMessage;
        shellEnvironment.putAll(source.getShellEnvironment());
    }

    public String getSummary() {
        if (summary!=null) return summary;
        return Strings.join(commands, " ; ");
    }
    
    public SshMachineLocation getMachine() {
        return machine;
    }
    
    public Map<String, String> getShellEnvironment() {
        return ImmutableMap.copyOf(shellEnvironment);
    }
 
    @Override
    public String toString() {
        return super.toString()+"["+getSummary()+"]";
    }

}