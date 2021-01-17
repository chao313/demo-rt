////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package demo.rt.sun.tools.jps;
//
//import org.junit.jupiter.api.Test;
//import sun.jvmstat.monitor.*;
//
//import java.io.*;
//import java.net.URISyntaxException;
//import java.util.Iterator;
//import java.util.Set;
//
///**
// * 自定义的JPS
// * 参考{@link sun.tools.jps.Jps}
// */
//public class LocalJps {
//
//    private static Arguments arguments;
//
//
//    public static OutputStream getId(String[] args) throws IOException, MonitorException {
//        OutputStream outputStream = new ByteArrayOutputStream();
//        try {
//            arguments = new Arguments(args);
//        } catch (IllegalArgumentException exception) {
//            System.err.println(exception.getMessage());
//            Arguments.printUsage(outputStream);
//            throw exception;
//        }
//        if (arguments.isHelp()) {
//            Arguments.printUsage(outputStream);
//        }
//
//        HostIdentifier hostIdentifier = arguments.hostId();
//        MonitoredHost monitoredHost = MonitoredHost.getMonitoredHost(hostIdentifier);
//        Set activeVms = monitoredHost.activeVms();
//        Iterator iterator = activeVms.iterator();
//
//        while (true) {
//            while (iterator.hasNext()) {
//                Integer next = (Integer) iterator.next();
//                StringBuilder builder = new StringBuilder();
//                Object o = null;
//                int next1 = next;
//                builder.append(String.valueOf(next1));
//                if (arguments.isQuiet()) {
//                    System.out.println(builder);
//                } else {
//                    MonitoredVm monitoredVm = null;
//                    String s = "//" + next1 + "?mode=r";
//                    String var11 = null;
//                    try {
//                        var11 = " -- process information unavailable";
//                        VmIdentifier var12 = new VmIdentifier(s);
//                        monitoredVm = monitoredHost.getMonitoredVm(var12, 0);
//                        var11 = " -- main class information unavailable";
//                        builder.append(" " + MonitoredVmUtil.mainClass(monitoredVm, arguments.showLongPaths()));
//                        String var13;
//                        if (arguments.showMainArgs()) {
//                            var11 = " -- main args information unavailable";
//                            var13 = MonitoredVmUtil.mainArgs(monitoredVm);
//                            if (var13 != null && var13.length() > 0) {
//                                builder.append(" " + var13);
//                            }
//                        }
//
//                        if (arguments.showVmArgs()) {
//                            var11 = " -- jvm args information unavailable";
//                            var13 = MonitoredVmUtil.jvmArgs(monitoredVm);
//                            if (var13 != null && var13.length() > 0) {
//                                builder.append(" " + var13);
//                            }
//                        }
//
//                        if (arguments.showVmFlags()) {
//                            var11 = " -- jvm flags information unavailable";
//                            var13 = MonitoredVmUtil.jvmFlags(monitoredVm);
//                            if (var13 != null && var13.length() > 0) {
//                                builder.append(" " + var13);
//                            }
//                        }
//
//                        var11 = " -- detach failed";
//                        monitoredHost.detach(monitoredVm);
//                        System.out.println(builder);
//                        outputStream.write(builder.toString().getBytes());
//                        outputStream.write("\n".getBytes());
//                        var11 = null;
//                    } catch (URISyntaxException var21) {
//                        o = var21;
//
//                        assert false;
//                    } catch (Exception var22) {
//                        o = var22;
//                    } finally {
//                        if (var11 != null) {
//                            builder.append(var11);
//                            if (arguments.isDebug() && o != null && ((Throwable) o).getMessage() != null) {
//                                builder.append("\n\t");
//                                builder.append(((Throwable) o).getMessage());
//                            }
//
//                            System.out.println(builder);
//                            if (arguments.printStackTrace()) {
//                                ((Throwable) o).printStackTrace();
//                            }
//                            continue;
//                        }
//                    }
//                }
//            }
//
//            return outputStream;
//        }
//    }
//
//    @Test
//    public void test() throws IOException, MonitorException {
//        OutputStream outputStream = LocalJps.getId(new String[]{"-l"});
//        InputStream is = new ByteArrayInputStream(outputStream.toString().getBytes());
//
//    }
//}
