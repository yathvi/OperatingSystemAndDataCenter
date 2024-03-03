import com.sun.tools.javac.Main;
import org.hyperic.sigar.*;
import org.hyperic.sigar.FileSystem;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.InternetProtocolStats;
import oshi.software.os.OSFileStore;
import oshi.software.os.OSProcess;
import oshi.software.os.OSThread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.management.*;
import java.lang.reflect.Field;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Objects;
/*import de.cluelessjoe.jsysinfo.CpuInfo;
import de.cluelessjoe.jsysinfo.JSysInfo;*/

public class SystemInfoPrinter {

    static int i = 0;
    static int j = 0;

    public static void main(String[] args) throws IOException
    {
        copySigarBin();
        System.setProperty("java.library.path", "target/sigar-bin");
        printAllProperties();
    }

    private static void printAllProperties() throws IOException {
        System.out.println("=========================");
        System.out.println("=== System Properties ===");
        System.out.println("=========================");
        java.util.Properties systemProperties = System.getProperties();
        systemProperties.forEach((key, value) -> System.out.println(key + ": " + value));
        System.out.println();System.out.println();System.out.println();
        System.out.println("==================");
        System.out.println("=== System Env ===");
        System.out.println("==================");
        System.getenv().forEach((key, value) -> System.out.println(key + ": " + value));
        System.out.println();System.out.println();System.out.println();

        System.gc();
        System.out.println("System.gc() -> The call System.gc() is effectively equivalent to the call: Runtime.getRuntime().gc()");
        System.out.println("Runs the garbage collector. Calling the gc method suggests that the Java Virtual Machine expend effort toward recycling unused objects in order to make the memory they currently occupy available for quick reuse. When control returns from the method call, the Java Virtual Machine has made a best effort to reclaim space from all discarded objects.");
        System.out.println();
        System.out.println("System.console(): " + System.console());
        if(Objects.nonNull(System.console()))
            System.out.println("System.console().toString(): " + System.console().toString());

        System.out.println("System.currentTimeMillis(): " + System.currentTimeMillis());
        System.out.println("System.nanoTime(): " + System.nanoTime());

        System.out.println("System.getSecurityManager(): " + System.getSecurityManager());
        if(Objects.nonNull(System.getSecurityManager()))
        {
            System.out.println("System.getSecurityManager().toString(): " + System.getSecurityManager().toString());
            System.out.println("System.getSecurityManager().getSecurityContext(): " + System.getSecurityManager().getSecurityContext());
            System.out.println("System.getSecurityManager().getSecurityContext().toString(): " + System.getSecurityManager().getSecurityContext().toString());
            System.out.println("System.getSecurityManager().getThreadGroup(): " + System.getSecurityManager().getThreadGroup());
            System.out.println("System.getSecurityManager().getThreadGroup().getName(): " + System.getSecurityManager().getThreadGroup().getName());
            System.out.println("System.getSecurityManager().getThreadGroup().toString(): " + System.getSecurityManager().getThreadGroup().toString());
            System.out.println("System.getSecurityManager().getThreadGroup().activeCount(): " + System.getSecurityManager().getThreadGroup().activeCount());
            System.out.println("System.getSecurityManager().getThreadGroup().activeGroupCount(): " + System.getSecurityManager().getThreadGroup().activeGroupCount());
            System.out.println("System.getSecurityManager().getThreadGroup().getMaxPriority(): " + System.getSecurityManager().getThreadGroup().getMaxPriority());
            System.out.println("System.getSecurityManager().getThreadGroup().isDaemon(): " + System.getSecurityManager().getThreadGroup().isDaemon());
            System.out.println("System.getSecurityManager().getThreadGroup().isDestroyed(): " + System.getSecurityManager().getThreadGroup().isDestroyed());
            System.out.println("System.getSecurityManager().getThreadGroup().getParent(): " + System.getSecurityManager().getThreadGroup().getParent());
            System.out.println("System.getSecurityManager().getThreadGroup().getParent().getName(): " + System.getSecurityManager().getThreadGroup().getParent().getName());
            System.out.println("System.getSecurityManager().getThreadGroup().getParent().toString(): " + System.getSecurityManager().getThreadGroup().getParent().toString());
            System.out.println("System.getSecurityManager().getThreadGroup().getParent().activeCount(): " + System.getSecurityManager().getThreadGroup().getParent().activeCount());
            System.out.println("System.getSecurityManager().getThreadGroup().getParent().activeGroupCount(): " + System.getSecurityManager().getThreadGroup().getParent().activeGroupCount());
            System.out.println("System.getSecurityManager().getThreadGroup().getParent().getMaxPriority(): " + System.getSecurityManager().getThreadGroup().getParent().getMaxPriority());
            System.out.println("System.getSecurityManager().getThreadGroup().getParent().isDaemon(): " + System.getSecurityManager().getThreadGroup().getParent().isDaemon());
            System.out.println("System.getSecurityManager().getThreadGroup().getParent().isDestroyed(): " + System.getSecurityManager().getThreadGroup().getParent().isDestroyed());
            System.out.println("System.getSecurityManager().getSecurityContext(): " + System.getSecurityManager().getSecurityContext());
            System.out.println("System.getSecurityManager().getSecurityContext().toString(): " + System.getSecurityManager().getSecurityContext().toString());
        }

        System.out.println("System.inheritedChannel(): " + System.inheritedChannel());
        if(Objects.nonNull(System.inheritedChannel()))
        {
            System.out.println("System.inheritedChannel().toString(): " + System.inheritedChannel().toString());
            System.out.println("System.inheritedChannel().isOpen(): " + System.inheritedChannel().isOpen());
        }

        System.out.println("System.lineSeparator(): " + System.lineSeparator());

        System.out.println();System.out.println();System.out.println();

        System.out.println("==================");
        System.out.println("===== Runtime ====");
        System.out.println("==================");

        System.out.println("Runtime.version(): " + Runtime.version());
        System.out.println("Runtime.getRuntime(): " + Runtime.getRuntime());
        System.out.println("Runtime.getRuntime().toString(): " + Runtime.getRuntime().toString());
        System.out.println();
        System.out.println("Returns the number of processors available to the Java virtual machine. This value may change during a particular invocation of the virtual machine. Applications that are sensitive to the number of available processors should therefore occasionally poll this property and adjust their resource usage appropriately. Returns: the maximum number of processors available to the virtual machine; never smaller than one");
        System.out.println("Runtime.getRuntime().availableProcessors(): " + Runtime.getRuntime().availableProcessors());
        System.out.println();
        System.out.println("Returns the amount of free memory in the Java Virtual Machine. Calling the gc method may result in increasing the value returned by freeMemory. Returns: an approximation to the total amount of memory currently available for future allocated objects, measured in bytes.");
        System.out.println("Runtime.getRuntime().freeMemory(): " + Runtime.getRuntime().freeMemory());
        System.out.println();
        System.out.println("Returns the maximum amount of memory that the Java virtual machine will attempt to use. If there is no inherent limit then the value Long.MAX_VALUE will be returned. Returns: the maximum amount of memory that the virtual machine will attempt to use, measured in bytes");
        System.out.println("Runtime.getRuntime().maxMemory(): " + Runtime.getRuntime().maxMemory());
        System.out.println();
        System.out.println("Returns the total amount of memory in the Java virtual machine. The value returned by this method may vary over time, depending on the host environment. Note that the amount of memory required to hold an object of any given type may be implementation-dependent. Returns: the total amount of memory currently available for current and future objects, measured in bytes.");
        System.out.println("Runtime.getRuntime().totalMemory(): " + Runtime.getRuntime().totalMemory());

        System.out.println();System.out.println();System.out.println();



        System.out.println("ManagementFactory.COMPILATION_MXBEAN_NAME: " + ManagementFactory.COMPILATION_MXBEAN_NAME);
        System.out.println("ManagementFactory.CLASS_LOADING_MXBEAN_NAME: " + ManagementFactory.CLASS_LOADING_MXBEAN_NAME);
        System.out.println("ManagementFactory.GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE: " + ManagementFactory.GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE);
        System.out.println("ManagementFactory.MEMORY_MANAGER_MXBEAN_DOMAIN_TYPE: " + ManagementFactory.MEMORY_MANAGER_MXBEAN_DOMAIN_TYPE);
        System.out.println("ManagementFactory.MEMORY_POOL_MXBEAN_DOMAIN_TYPE: " + ManagementFactory.MEMORY_POOL_MXBEAN_DOMAIN_TYPE);
        System.out.println("ManagementFactory.MEMORY_MXBEAN_NAME: " + ManagementFactory.MEMORY_MXBEAN_NAME);
        System.out.println("ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME: " + ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME);
        System.out.println("ManagementFactory.THREAD_MXBEAN_NAME: " + ManagementFactory.THREAD_MXBEAN_NAME);
        System.out.println("ManagementFactory.RUNTIME_MXBEAN_NAME: " + ManagementFactory.RUNTIME_MXBEAN_NAME);

        System.out.println();System.out.println();System.out.println();


        System.out.println("ManagementFactory.getOperatingSystemMXBean(): " + ManagementFactory.getOperatingSystemMXBean());
        System.out.println("ManagementFactory.getOperatingSystemMXBean().toString(): " + ManagementFactory.getOperatingSystemMXBean().toString());
        System.out.println("ManagementFactory.getOperatingSystemMXBean().getName(): " + ManagementFactory.getOperatingSystemMXBean().getName());
        System.out.println("ManagementFactory.getOperatingSystemMXBean().getArch(): " + ManagementFactory.getOperatingSystemMXBean().getArch());
        System.out.println("ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage(): " + ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage());
        System.out.println("ManagementFactory.getOperatingSystemMXBean().getVersion(): " + ManagementFactory.getOperatingSystemMXBean().getVersion());
        System.out.println("ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors(): " + ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors());

        System.out.println();System.out.println();System.out.println();

        com.sun.management.OperatingSystemMXBean sunManagementOperatingSystemMXBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        System.out.println("com.sun.management.OperatingSystemMXBean: " + sunManagementOperatingSystemMXBean);
        System.out.println("com.sun.management.OperatingSystemMXBean.toString(): " + sunManagementOperatingSystemMXBean.toString());
        System.out.println("com.sun.management.OperatingSystemMXBean.getName(): " + sunManagementOperatingSystemMXBean.getName());
        System.out.println("com.sun.management.OperatingSystemMXBean.getArch(): " + sunManagementOperatingSystemMXBean.getArch());
        System.out.println("com.sun.management.OperatingSystemMXBean.getAvailableProcessors(): " + sunManagementOperatingSystemMXBean.getAvailableProcessors());
        System.out.println("com.sun.management.OperatingSystemMXBean.getFreeSwapSpaceSize(): " + sunManagementOperatingSystemMXBean.getFreeSwapSpaceSize());
        System.out.println("com.sun.management.OperatingSystemMXBean.getTotalSwapSpaceSize(): " + sunManagementOperatingSystemMXBean.getTotalSwapSpaceSize());
        System.out.println("com.sun.management.OperatingSystemMXBean.getObjectName(): " + sunManagementOperatingSystemMXBean.getObjectName());
        System.out.println();
        System.out.println("Returns the amount of virtual memory that is guaranteed to be available to the running process in bytes, or -1 if this operation is not supported. Returns: the amount of virtual memory that is guaranteed to be available to the running process in bytes, or -1 if this operation is not supported.");
        System.out.println("com.sun.management.OperatingSystemMXBean.getCommittedVirtualMemorySize(): " + sunManagementOperatingSystemMXBean.getCommittedVirtualMemorySize());
        System.out.println();
        System.out.println("com.sun.management.OperatingSystemMXBean.getVersion(): " + sunManagementOperatingSystemMXBean.getVersion());
        System.out.println("com.sun.management.OperatingSystemMXBean.getTotalPhysicalMemorySize(): " + sunManagementOperatingSystemMXBean.getTotalPhysicalMemorySize());
        System.out.println("com.sun.management.OperatingSystemMXBean.getFreePhysicalMemorySize(): " + sunManagementOperatingSystemMXBean.getFreePhysicalMemorySize());
        System.out.println("com.sun.management.OperatingSystemMXBean.getSystemLoadAverage(): " + sunManagementOperatingSystemMXBean.getSystemLoadAverage());
        System.out.println("com.sun.management.OperatingSystemMXBean.getProcessCpuLoad(): " + sunManagementOperatingSystemMXBean.getProcessCpuLoad());
        System.out.println("com.sun.management.OperatingSystemMXBean.getSystemCpuLoad(): " + sunManagementOperatingSystemMXBean.getSystemCpuLoad());
        System.out.println("com.sun.management.OperatingSystemMXBean.getProcessCpuTime(): " + sunManagementOperatingSystemMXBean.getProcessCpuTime());


        System.out.println();System.out.println();System.out.println();



        System.out.println("ManagementFactory.getRuntimeMXBean(): " + ManagementFactory.getRuntimeMXBean());
        System.out.println("ManagementFactory.getRuntimeMXBean().toString(): " + ManagementFactory.getRuntimeMXBean().toString());
        System.out.println("ManagementFactory.getRuntimeMXBean().getName(): " + ManagementFactory.getRuntimeMXBean().getName());
        System.out.println("ManagementFactory.getRuntimeMXBean().getVmVendor(): " + ManagementFactory.getRuntimeMXBean().getVmVendor());
        System.out.println("ManagementFactory.getRuntimeMXBean().getVmVersion(): " + ManagementFactory.getRuntimeMXBean().getVmVersion());
        System.out.println("ManagementFactory.getRuntimeMXBean().getVmName(): " + ManagementFactory.getRuntimeMXBean().getVmName());

        System.out.println();System.out.println();System.out.println();

        System.out.println("ManagementFactory.getRuntimeMXBean().getSystemProperties(): " + ManagementFactory.getRuntimeMXBean().getSystemProperties());
        System.out.println("ManagementFactory.getRuntimeMXBean().getSystemProperties().toString(): " + ManagementFactory.getRuntimeMXBean().getSystemProperties().toString());
        System.out.println("ManagementFactory.getRuntimeMXBean().getSystemProperties().size(): " + ManagementFactory.getRuntimeMXBean().getSystemProperties().size());
        ManagementFactory.getRuntimeMXBean().getSystemProperties().forEach((key,value)-> System.out.println(key+" : "+value));

        System.out.println();System.out.println();System.out.println();

        System.out.println("ManagementFactory.getRuntimeMXBean().isBootClassPathSupported(): " + ManagementFactory.getRuntimeMXBean().isBootClassPathSupported());
        if(ManagementFactory.getRuntimeMXBean().isBootClassPathSupported())
        {
            System.out.println("ManagementFactory.getRuntimeMXBean().getBootClassPath(): " + ManagementFactory.getRuntimeMXBean().getBootClassPath());
        }
        System.out.println("ManagementFactory.getRuntimeMXBean().getClassPath(): " + ManagementFactory.getRuntimeMXBean().getClassPath());
        System.out.println("ManagementFactory.getRuntimeMXBean().getLibraryPath(): " + ManagementFactory.getRuntimeMXBean().getLibraryPath());
        System.out.println("ManagementFactory.getRuntimeMXBean().getSpecName(): " + ManagementFactory.getRuntimeMXBean().getSpecName());
        System.out.println("ManagementFactory.getRuntimeMXBean().getSpecVendor(): " + ManagementFactory.getRuntimeMXBean().getSpecVendor());
        System.out.println("ManagementFactory.getRuntimeMXBean().getSpecVersion(): " + ManagementFactory.getRuntimeMXBean().getSpecVersion());
        System.out.println("ManagementFactory.getRuntimeMXBean().getManagementSpecVersion(): " + ManagementFactory.getRuntimeMXBean().getManagementSpecVersion());
        System.out.println("ManagementFactory.getRuntimeMXBean().getStartTime(): " + ManagementFactory.getRuntimeMXBean().getStartTime());
        System.out.println("ManagementFactory.getRuntimeMXBean().getUptime(): " + ManagementFactory.getRuntimeMXBean().getUptime());

        System.out.println("ManagementFactory.getRuntimeMXBean().getObjectName(): " + ManagementFactory.getRuntimeMXBean().getObjectName());
        System.out.println("ManagementFactory.getRuntimeMXBean().getObjectName().toString(): " + ManagementFactory.getRuntimeMXBean().getObjectName().toString());
        System.out.println("ManagementFactory.getRuntimeMXBean().getObjectName().getCanonicalName(): " + ManagementFactory.getRuntimeMXBean().getObjectName().getCanonicalName());
        System.out.println("ManagementFactory.getRuntimeMXBean().getObjectName().getCanonicalKeyPropertyListString(): " + ManagementFactory.getRuntimeMXBean().getObjectName().getCanonicalKeyPropertyListString());
        System.out.println("ManagementFactory.getRuntimeMXBean().getObjectName().hashCode(): " + ManagementFactory.getRuntimeMXBean().getObjectName().hashCode());
        System.out.println("ManagementFactory.getRuntimeMXBean().getObjectName().getDomain(): " + ManagementFactory.getRuntimeMXBean().getObjectName().getDomain());
        System.out.println("ManagementFactory.getRuntimeMXBean().getObjectName().getKeyPropertyListString(): " + ManagementFactory.getRuntimeMXBean().getObjectName().getKeyPropertyListString());
        System.out.println("ManagementFactory.getRuntimeMXBean().getObjectName().getKeyPropertyList(): " + ManagementFactory.getRuntimeMXBean().getObjectName().getKeyPropertyList());
        System.out.println("ManagementFactory.getRuntimeMXBean().getObjectName().isDomainPattern(): " + ManagementFactory.getRuntimeMXBean().getObjectName().isDomainPattern());
        System.out.println("ManagementFactory.getRuntimeMXBean().getObjectName().isPattern(): " + ManagementFactory.getRuntimeMXBean().getObjectName().isPattern());
        System.out.println("ManagementFactory.getRuntimeMXBean().getObjectName().isPropertyListPattern(): " + ManagementFactory.getRuntimeMXBean().getObjectName().isPropertyListPattern());
        System.out.println("ManagementFactory.getRuntimeMXBean().getObjectName().isPropertyValuePattern: " + ManagementFactory.getRuntimeMXBean().getObjectName().isPropertyValuePattern());
        System.out.println("ManagementFactory.getRuntimeMXBean().getObjectName().isPropertyPattern: " + ManagementFactory.getRuntimeMXBean().getObjectName().isPropertyPattern());
        System.out.println("ManagementFactory.getRuntimeMXBean().getObjectName().getKeyPropertyList(): " + ManagementFactory.getRuntimeMXBean().getObjectName().getKeyPropertyList());
        ManagementFactory.getRuntimeMXBean().getObjectName().getKeyPropertyList().forEach((key,value)-> System.out.println("ManagementFactory.getRuntimeMXBean().getObjectName().getKeyPropertyList()."+key+": "+value));

        System.out.println("ManagementFactory.getRuntimeMXBean().getInputArguments(): " + ManagementFactory.getRuntimeMXBean().getInputArguments());
        ManagementFactory.getRuntimeMXBean().getInputArguments().forEach(value-> System.out.println("ManagementFactory.getRuntimeMXBean().getInputArguments() values: "+ value));

        System.out.println();System.out.println();System.out.println();


        System.out.println("ManagementFactory.getPlatformMBeanServer(): " + ManagementFactory.getPlatformMBeanServer());
        System.out.println("ManagementFactory.getPlatformMBeanServer().toString(): " + ManagementFactory.getPlatformMBeanServer().toString());
        System.out.println("ManagementFactory.getPlatformMBeanServer().getClassLoaderRepository(): " + ManagementFactory.getPlatformMBeanServer().getClassLoaderRepository());
        System.out.println("ManagementFactory.getPlatformMBeanServer().getDefaultDomain(): " + ManagementFactory.getPlatformMBeanServer().getDefaultDomain());
        System.out.println("ManagementFactory.getPlatformMBeanServer().getMBeanCount(): " + ManagementFactory.getPlatformMBeanServer().getMBeanCount());
        System.out.println("ManagementFactory.getPlatformMBeanServer().getDomains(): " + Arrays.toString(ManagementFactory.getPlatformMBeanServer().getDomains()));


        System.out.println();System.out.println();System.out.println();


        System.out.println("ManagementFactory.getThreadMXBean(): " + ManagementFactory.getThreadMXBean());
        System.out.println("ManagementFactory.getThreadMXBean().toString(): " + ManagementFactory.getThreadMXBean().toString());
        System.out.println("ManagementFactory.getThreadMXBean().getDaemonThreadCount(): " + ManagementFactory.getThreadMXBean().getDaemonThreadCount());
        System.out.println("ManagementFactory.getThreadMXBean().getPeakThreadCount(): " + ManagementFactory.getThreadMXBean().getPeakThreadCount());
        System.out.println("ManagementFactory.getThreadMXBean().getThreadCount(): " + ManagementFactory.getThreadMXBean().getThreadCount());
        System.out.println("ManagementFactory.getThreadMXBean().getTotalStartedThreadCount(): " + ManagementFactory.getThreadMXBean().getTotalStartedThreadCount());
        System.out.println("ManagementFactory.getThreadMXBean().isObjectMonitorUsageSupported: " + ManagementFactory.getThreadMXBean().isObjectMonitorUsageSupported());
        System.out.println("ManagementFactory.getThreadMXBean().isSynchronizerUsageSupported(): " + ManagementFactory.getThreadMXBean().isSynchronizerUsageSupported());
        System.out.println("ManagementFactory.getThreadMXBean().isThreadContentionMonitoringEnabled(): " + ManagementFactory.getThreadMXBean().isThreadContentionMonitoringEnabled());
        System.out.println("ManagementFactory.getThreadMXBean().isThreadContentionMonitoringSupported(): " + ManagementFactory.getThreadMXBean().isThreadContentionMonitoringSupported());
        System.out.println("ManagementFactory.getThreadMXBean().isThreadCpuTimeEnabled(): " + ManagementFactory.getThreadMXBean().isThreadCpuTimeEnabled());
        System.out.println("ManagementFactory.getThreadMXBean().isThreadCpuTimeSupported(): " + ManagementFactory.getThreadMXBean().isThreadCpuTimeSupported());

        System.out.println();System.out.println();System.out.println();

        System.out.println("ManagementFactory.getThreadMXBean().findDeadlockedThreads(): " + Arrays.toString(ManagementFactory.getThreadMXBean().findDeadlockedThreads()));
        if(Objects.nonNull(ManagementFactory.getThreadMXBean().findDeadlockedThreads()))
            for (long threadId: ManagementFactory.getThreadMXBean().findDeadlockedThreads())
            {
                System.out.println("ManagementFactory.getThreadMXBean().findDeadlockedThreads() threadId : " + threadId);
            }

        System.out.println();System.out.println();System.out.println();


        System.out.println("ManagementFactory.getThreadMXBean().findMonitorDeadlockedThreads(): " + Arrays.toString(ManagementFactory.getThreadMXBean().findMonitorDeadlockedThreads()));
        if(Objects.nonNull(ManagementFactory.getThreadMXBean().findMonitorDeadlockedThreads()))
            for (long threadId: ManagementFactory.getThreadMXBean().findMonitorDeadlockedThreads())
            {
                System.out.println("ManagementFactory.getThreadMXBean().findMonitorDeadlockedThreads() threadId : " + threadId);
            }

        System.out.println();System.out.println();System.out.println();


        System.out.println("ManagementFactory.getThreadMXBean().getAllThreadIds(): " + Arrays.toString(ManagementFactory.getThreadMXBean().getAllThreadIds()));
        if(Objects.nonNull(ManagementFactory.getThreadMXBean().getAllThreadIds()))
            for (long threadId: ManagementFactory.getThreadMXBean().getAllThreadIds())
            {
                System.out.println("ManagementFactory.getThreadMXBean().getAllThreadIds() threadId : " + threadId);
            }

        ThreadInfo[] threadInfos = ManagementFactory.getThreadMXBean().getThreadInfo(ManagementFactory.getThreadMXBean().getAllThreadIds());

        i=0;
        for(ThreadInfo threadInfo : threadInfos)
        {
            System.out.println("<=======Start=======>");
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"]: " + threadInfo);
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getThreadId(): " + threadInfo.getThreadId());
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getThreadName(): " + threadInfo.getThreadName());
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].toString(): " + threadInfo.toString());
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockName(): " + threadInfo.getLockName());
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockInfo(): " + threadInfo.getLockInfo());
            if(Objects.nonNull(threadInfo.getLockInfo()))
            {
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockInfo().toString(): " + threadInfo.getLockInfo().toString());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockInfo().getClassName(): " + threadInfo.getLockInfo().getClassName());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockInfo().getIdentityHashCode(): " + threadInfo.getLockInfo().getIdentityHashCode());
            }
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockOwnerName(): " + threadInfo.getLockOwnerName());
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockOwnerId(): " + threadInfo.getLockOwnerId());
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getBlockedCount(): " + threadInfo.getBlockedCount());
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getBlockedTime(): " + threadInfo.getBlockedTime());
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getPriority(): " + threadInfo.getPriority());
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getThreadState(): " + threadInfo.getThreadState());
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getThreadState().name(): " + threadInfo.getThreadState().name());
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getThreadState().toString(): " + threadInfo.getThreadState().toString());
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getThreadState().hashCode(): " + threadInfo.getThreadState().hashCode());
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getThreadState().ordinal(): " + threadInfo.getThreadState().ordinal());
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getWaitedCount(): " + threadInfo.getWaitedCount());
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getWaitedTime(): " + threadInfo.getWaitedTime());
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].isDaemon(): " + threadInfo.isDaemon());
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].isInNative(): " + threadInfo.isInNative());
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].isSuspended(): " + threadInfo.isSuspended());
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedMonitors(): " + Arrays.toString(threadInfo.getLockedMonitors()));
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedMonitors().toString(): " + threadInfo.getLockedMonitors().toString());
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedMonitors().length: " + threadInfo.getLockedMonitors().length);
            j=0;
            for(MonitorInfo monitorInfo : threadInfo.getLockedMonitors())
            {
                System.out.println("<=======Start=======>");
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedMonitors()["+j+"]: " + monitorInfo);
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedMonitors()["+j+"].getClassName(): " + monitorInfo.getClassName());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedMonitors()["+j+"].toString(): " + monitorInfo.toString());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedMonitors()["+j+"].getIdentityHashCode: " + monitorInfo.getIdentityHashCode());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedMonitors()["+j+"].getLockedStackDepth(): " + monitorInfo.getLockedStackDepth());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedMonitors()["+j+"].getLockedStackFrame(): " + monitorInfo.getLockedStackFrame());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedMonitors()["+j+"].getLockedStackFrame().toString(): " + monitorInfo.getLockedStackFrame().toString());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedMonitors()["+j+"].getLockedStackFrame().getClassName(): " + monitorInfo.getLockedStackFrame().getClassName());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedMonitors()["+j+"].getLockedStackFrame().getFileName(): " + monitorInfo.getLockedStackFrame().getFileName());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedMonitors()["+j+"].getLockedStackFrame().getMethodName(): " + monitorInfo.getLockedStackFrame().getMethodName());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedMonitors()["+j+"].getLockedStackFrame().hashCode(): " + monitorInfo.getLockedStackFrame().hashCode());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedMonitors()["+j+"].getLockedStackFrame().getClassLoaderName(): " + monitorInfo.getLockedStackFrame().getClassLoaderName());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedMonitors()["+j+"].getLockedStackFrame().getModuleName(): " + monitorInfo.getLockedStackFrame().getModuleName());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedMonitors()["+j+"].getLockedStackFrame().getModuleVersion(): " + monitorInfo.getLockedStackFrame().getModuleVersion());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedMonitors()["+j+"].getLockedStackFrame().getLineNumber(): " + monitorInfo.getLockedStackFrame().getLineNumber());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedMonitors()["+j+"].getLockedStackFrame().isNativeMethod(): " + monitorInfo.getLockedStackFrame().isNativeMethod());
                j++;
                System.out.println("<=======End=======>");
            }

            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedSynchronizers(): " + Arrays.toString(threadInfo.getLockedSynchronizers()));
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedSynchronizers().toString(): " + threadInfo.getLockedSynchronizers().toString());
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedSynchronizers().length: " + threadInfo.getLockedSynchronizers().length);

            j=0;
            for(LockInfo lockInfo : threadInfo.getLockedSynchronizers())
            {
                System.out.println("<=======Start=======>");
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedSynchronizers()["+j+"]: " + lockInfo);
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedSynchronizers()["+j+"].toString(): " + lockInfo.toString());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedSynchronizers()["+j+"].getClassName(): " + lockInfo.getClassName());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getLockedSynchronizers()["+j+"].getIdentityHashCode(): " + lockInfo.getIdentityHashCode());
                j++;
                System.out.println("<=======End=======>");
            }

            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getStackTrace(): " + Arrays.toString(threadInfo.getStackTrace()));
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getStackTrace().toString(): " + threadInfo.getStackTrace().toString());
            System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getStackTrace().length: " + threadInfo.getStackTrace().length);
            j=0;
            for(StackTraceElement stackTraceElement : threadInfo.getStackTrace())
            {
                System.out.println("<=======Start=======>");
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getStackTrace()["+j+"]: " + stackTraceElement);
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getStackTrace()["+j+"].toString(): " + stackTraceElement.toString());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getStackTrace()["+j+"].getClassName(): " + stackTraceElement.getClassName());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getStackTrace()["+j+"].getFileName(): " + stackTraceElement.getFileName());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getStackTrace()["+j+"].getMethodName(): " + stackTraceElement.getMethodName());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getStackTrace()["+j+"].hashCode(): " + stackTraceElement.hashCode());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getStackTrace()["+j+"].getClassLoaderName(): " + stackTraceElement.getClassLoaderName());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getStackTrace()["+j+"].getModuleName(): " + stackTraceElement.getModuleName());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getStackTrace()["+j+"].getModuleVersion(): " + stackTraceElement.getModuleVersion());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getStackTrace()["+j+"].getLineNumber(): " + stackTraceElement.getLineNumber());
                System.out.println("ManagementFactory.getThreadMXBean().getThreadInfo()["+i+"].getStackTrace()["+j+"].isNativeMethod(): " + stackTraceElement.isNativeMethod());
                j++;
                System.out.println("<=======End=======>");
            }
            i++;
            System.out.println("<=======End=======>");
        }


        System.out.println();System.out.println();System.out.println();

        System.out.println("User time vs system time , Real time vs CPU time -> ");
        System.out.println("    https://en.wikipedia.org/wiki/Time_(Unix)");
        System.out.println("In most systems, system calls can only be made from userspace processes -> ");
        System.out.println("Many modern operating systems have hundreds of system calls. ->");
        System.out.println("    https://en.wikipedia.org/wiki/System_call");
        System.out.println();
        System.out.println("ManagementFactory.getThreadMXBean().isCurrentThreadCpuTimeSupported(): " + ManagementFactory.getThreadMXBean().isCurrentThreadCpuTimeSupported());
        if(ManagementFactory.getThreadMXBean().isCurrentThreadCpuTimeSupported())
        {
            System.out.println("ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime(): " + ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime());
            System.out.println("ManagementFactory.getThreadMXBean().getCurrentThreadUserTime(): " + ManagementFactory.getThreadMXBean().getCurrentThreadUserTime());
        }

        System.out.println();System.out.println();System.out.println();

        System.out.println("ManagementFactory.getClassLoadingMXBean(): " + ManagementFactory.getClassLoadingMXBean());
        System.out.println("ManagementFactory.getClassLoadingMXBean().toString(): " + ManagementFactory.getClassLoadingMXBean().toString());
        System.out.println("ManagementFactory.getClassLoadingMXBean().getLoadedClassCount(): " + ManagementFactory.getClassLoadingMXBean().getLoadedClassCount());
        System.out.println("ManagementFactory.getClassLoadingMXBean().getTotalLoadedClassCount(): " + ManagementFactory.getClassLoadingMXBean().getTotalLoadedClassCount());
        System.out.println("ManagementFactory.getClassLoadingMXBean().getUnloadedClassCount(): " + ManagementFactory.getClassLoadingMXBean().getUnloadedClassCount());
        System.out.println("ManagementFactory.getClassLoadingMXBean().isVerbose(): " + ManagementFactory.getClassLoadingMXBean().isVerbose());

        System.out.println();System.out.println();System.out.println();

        System.out.println("ManagementFactory.getCompilationMXBean(): " + ManagementFactory.getCompilationMXBean());
        System.out.println("ManagementFactory.getCompilationMXBean().toString(): " + ManagementFactory.getCompilationMXBean().toString());
        System.out.println("ManagementFactory.getCompilationMXBean().getName(): " + ManagementFactory.getCompilationMXBean().getName());
        System.out.println("ManagementFactory.getCompilationMXBean().getTotalCompilationTime(): " + ManagementFactory.getCompilationMXBean().getTotalCompilationTime());
        System.out.println("ManagementFactory.getCompilationMXBean().isCompilationTimeMonitoringSupported(): " + ManagementFactory.getCompilationMXBean().isCompilationTimeMonitoringSupported());

        System.out.println();System.out.println();System.out.println();

        System.out.println("ManagementFactory.getMemoryMXBean(): " + ManagementFactory.getMemoryMXBean());
        System.out.println("ManagementFactory.getMemoryMXBean().toString(): " + ManagementFactory.getMemoryMXBean().toString());
        System.out.println("ManagementFactory.getMemoryMXBean().getObjectPendingFinalizationCount(): " + ManagementFactory.getMemoryMXBean().getObjectPendingFinalizationCount());
        System.out.println("ManagementFactory.getMemoryMXBean().isVerbose(): " + ManagementFactory.getMemoryMXBean().isVerbose());
        System.out.println("ManagementFactory.getMemoryMXBean().gc() equivalent to System.gc()");
        System.out.println();System.out.println();
        System.out.println("Check below link for -> init(-Xms) , used , committed , max(-Xmx)");
        System.out.println("https://docs.oracle.com/javase/8/docs/api/java/lang/management/MemoryUsage.html");
        System.out.println();
        System.out.println("ManagementFactory.getMemoryMXBean().getHeapMemoryUsage(): " + ManagementFactory.getMemoryMXBean().getHeapMemoryUsage());
        System.out.println("ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().toString(): " + ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().toString());
        System.out.println("ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax(): " + ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax());
        System.out.println("ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed(): " + ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed());
        System.out.println("ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getCommitted(): " + ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getCommitted());
        System.out.println("ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getInit(): " + ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getInit());
        System.out.println("ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage(): " + ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage());
        System.out.println("ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().toString(): " + ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().toString());
        System.out.println("ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getMax(): " + ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getMax());
        System.out.println("ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getUsed(): " + ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getUsed());
        System.out.println("ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getCommitted(): " + ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getCommitted());
        System.out.println("ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getInit(): " + ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getInit());

        System.out.println();System.out.println();System.out.println();

        System.out.println("ManagementFactory.getGarbageCollectorMXBeans(): " + ManagementFactory.getGarbageCollectorMXBeans());
        System.out.println("ManagementFactory.getGarbageCollectorMXBeans().toString(): " + ManagementFactory.getGarbageCollectorMXBeans().toString());
        System.out.println("ManagementFactory.getGarbageCollectorMXBeans().size(): " + ManagementFactory.getGarbageCollectorMXBeans().size());
        i=0;
        for(GarbageCollectorMXBean garbageCollectorMXBeans : ManagementFactory.getGarbageCollectorMXBeans())
        {
            System.out.println("<=======Start=======>");
            System.out.println("ManagementFactory.getGarbageCollectorMXBeans()["+i+"].getName(): " + garbageCollectorMXBeans.getName());
            System.out.println("ManagementFactory.getGarbageCollectorMXBeans()["+i+"].getCollectionCount(): " + garbageCollectorMXBeans.getCollectionCount());
            System.out.println("ManagementFactory.getGarbageCollectorMXBeans()["+i+"].getCollectionTime(): " + garbageCollectorMXBeans.getCollectionTime());
            System.out.println("ManagementFactory.getGarbageCollectorMXBeans()["+i+"].getMemoryPoolNames(): " + Arrays.toString(garbageCollectorMXBeans.getMemoryPoolNames()));
            System.out.println("ManagementFactory.getGarbageCollectorMXBeans()["+i+"].getObjectName(): " + garbageCollectorMXBeans.getObjectName());
            System.out.println("ManagementFactory.getGarbageCollectorMXBeans()["+i+"].isValid(): " + garbageCollectorMXBeans.isValid());
            i++;
            System.out.println("<=======End=======>");
        }

        System.out.println();System.out.println();System.out.println();

        System.out.println("ManagementFactory.getMemoryManagerMXBeans(): " + ManagementFactory.getMemoryManagerMXBeans());
        System.out.println("ManagementFactory.getMemoryManagerMXBeans().toString(): " + ManagementFactory.getMemoryManagerMXBeans().toString());
        System.out.println("ManagementFactory.getMemoryManagerMXBeans().size(): " + ManagementFactory.getMemoryManagerMXBeans().size());
        i=0;
        for(MemoryManagerMXBean memoryManagerMXBean : ManagementFactory.getMemoryManagerMXBeans())
        {
            System.out.println("<=======Start=======>");
            System.out.println("ManagementFactory.getMemoryManagerMXBeans()["+i+"].getName(): " + memoryManagerMXBean.getName());
            System.out.println("ManagementFactory.getMemoryManagerMXBeans()["+i+"].getMemoryPoolNames(): " + Arrays.toString(memoryManagerMXBean.getMemoryPoolNames()));
            System.out.println("ManagementFactory.getMemoryManagerMXBeans()["+i+"].getObjectName(): " + memoryManagerMXBean.getObjectName());
            System.out.println("ManagementFactory.getMemoryManagerMXBeans()["+i+"].isValid(): " + memoryManagerMXBean.isValid());
            i++;
            System.out.println("<=======End=======>");
        }

        System.out.println();System.out.println();System.out.println();


        System.out.println("ManagementFactory.getMemoryPoolMXBeans(): " + ManagementFactory.getMemoryPoolMXBeans());
        System.out.println("ManagementFactory.getMemoryPoolMXBeans().toString(): " + ManagementFactory.getMemoryPoolMXBeans().toString());
        System.out.println("ManagementFactory.getMemoryPoolMXBeans().size(): " + ManagementFactory.getMemoryPoolMXBeans().size());
        i=0;
        for(MemoryPoolMXBean memoryPoolMXBean : ManagementFactory.getMemoryPoolMXBeans())
        {
            System.out.println("<=======Start=======>");
            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getName(): " + memoryPoolMXBean.getName());
            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getType(): " + memoryPoolMXBean.getType());
            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getType().toString(): " + memoryPoolMXBean.getType().toString());
            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getType().name(): " + memoryPoolMXBean.getType().name());

            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getObjectName(): " + memoryPoolMXBean.getObjectName());
            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].isValid(): " + memoryPoolMXBean.isValid());
            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getCollectionUsage(): " + memoryPoolMXBean.getCollectionUsage());
            if(Objects.nonNull(memoryPoolMXBean.getCollectionUsage()))
            {
                System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getCollectionUsage().toString(): " + memoryPoolMXBean.getCollectionUsage().toString());
                System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getCollectionUsage().getInit(): " + memoryPoolMXBean.getCollectionUsage().getInit());
                System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getCollectionUsage().getMax(): " + memoryPoolMXBean.getCollectionUsage().getMax());
                System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getCollectionUsage().getUsed(): " + memoryPoolMXBean.getCollectionUsage().getUsed());
                System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getCollectionUsage().getCommitted(): " + memoryPoolMXBean.getCollectionUsage().getCommitted());
            }

            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].isCollectionUsageThresholdSupported(): " + memoryPoolMXBean.isCollectionUsageThresholdSupported());
            if(memoryPoolMXBean.isCollectionUsageThresholdSupported())
            {
                System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getCollectionUsageThreshold(): " + memoryPoolMXBean.getCollectionUsageThreshold());
                System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getCollectionUsageThresholdCount(): " + memoryPoolMXBean.getCollectionUsageThresholdCount());
                System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].isCollectionUsageThresholdExceeded(): " + memoryPoolMXBean.isCollectionUsageThresholdExceeded());
            }

            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getMemoryManagerNames(): " + Arrays.toString(memoryPoolMXBean.getMemoryManagerNames()));

            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getUsage(): " + memoryPoolMXBean.getUsage());
            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getUsage().toString(): " + memoryPoolMXBean.getUsage().toString());
            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getUsage().getCommitted(): " + memoryPoolMXBean.getUsage().getCommitted());
            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getUsage().getMax(): " + memoryPoolMXBean.getUsage().getMax());
            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getUsage().getInit(): " + memoryPoolMXBean.getUsage().getInit());
            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getUsage().getUsed(): " + memoryPoolMXBean.getUsage().getUsed());


            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getPeakUsage(): " + memoryPoolMXBean.getPeakUsage());
            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getPeakUsage().toString(): " + memoryPoolMXBean.getPeakUsage().toString());
            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getPeakUsage().getCommitted(): " + memoryPoolMXBean.getPeakUsage().getCommitted());
            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getPeakUsage().getMax(): " + memoryPoolMXBean.getPeakUsage().getMax());
            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getPeakUsage().getInit(): " + memoryPoolMXBean.getPeakUsage().getInit());
            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getPeakUsage().getUsed(): " + memoryPoolMXBean.getPeakUsage().getUsed());

            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].isUsageThresholdSupported(): " + memoryPoolMXBean.isUsageThresholdSupported());
            if(memoryPoolMXBean.isUsageThresholdSupported())
            {
                System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getUsageThreshold(): " + memoryPoolMXBean.getUsageThreshold());
                System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].getUsageThresholdCount(): " + memoryPoolMXBean.getUsageThresholdCount());
                System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].isUsageThresholdExceeded(): " + memoryPoolMXBean.isUsageThresholdExceeded());
            }

            System.out.println("ManagementFactory.getMemoryPoolMXBeans()["+i+"].resetPeakUsage() -> Resets the peak memory usage statistic of this memory pool to the current memory usage.");
            memoryPoolMXBean.resetPeakUsage();
            i++;
            System.out.println("<=======End=======>");
        }

        System.out.println();System.out.println();System.out.println();

        System.out.println("ManagementFactory.getPlatformManagementInterfaces(): " + ManagementFactory.getPlatformManagementInterfaces());
        System.out.println("ManagementFactory.getPlatformManagementInterfaces().toString(): " + ManagementFactory.getPlatformManagementInterfaces().toString());
        System.out.println("ManagementFactory.getPlatformManagementInterfaces().size(): " + ManagementFactory.getPlatformManagementInterfaces().size());
        i=0;
        for(Class<? extends PlatformManagedObject> platformManagedObject: ManagementFactory.getPlatformManagementInterfaces())
        {
            System.out.println("<=======Start=======>");
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getName(): " + platformManagedObject.getName());
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getTypeName(): " + platformManagedObject.getTypeName());
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getCanonicalName(): " + platformManagedObject.getCanonicalName());
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getSimpleName(): " + platformManagedObject.getSimpleName());
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getTypeName(): " + platformManagedObject.getTypeName());
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getMethods(): " + Arrays.toString(platformManagedObject.getMethods()));
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getDeclaredMethods(): " + Arrays.toString(platformManagedObject.getDeclaredMethods()));
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].toGenericString(): " + platformManagedObject.toGenericString());
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getPackageName(): " + platformManagedObject.getPackageName());
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getAnnotatedInterfaces(): " + Arrays.toString(platformManagedObject.getAnnotatedInterfaces()));
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].desiredAssertionStatus(): " + platformManagedObject.desiredAssertionStatus());
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].isAnnotation(): " + platformManagedObject.isAnnotation());

            if(platformManagedObject.isAnnotation())
            {
                System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getAnnotatedSuperclass().getType(): " + platformManagedObject.getAnnotatedSuperclass().getType());
                System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getAnnotatedSuperclass().toString(): " + platformManagedObject.getAnnotatedSuperclass().toString());
                System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getAnnotatedSuperclass().getAnnotations(): " + Arrays.toString(platformManagedObject.getAnnotatedSuperclass().getAnnotations()));
                System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getAnnotatedSuperclass().getAnnotatedOwnerType(): " + platformManagedObject.getAnnotatedSuperclass().getAnnotatedOwnerType());
                System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getAnnotatedSuperclass().getDeclaredAnnotations(): " + Arrays.toString(platformManagedObject.getAnnotatedSuperclass().getDeclaredAnnotations()));
            }
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getDeclaredConstructors(): " + platformManagedObject.getDeclaredConstructors());
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getDeclaredConstructors().length: " + platformManagedObject.getDeclaredConstructors().length);

            if(platformManagedObject.getDeclaredConstructors().length > 0)
            {
                for (java.lang.reflect.Constructor<?> reflectConstructor: platformManagedObject.getDeclaredConstructors())
                {
                    System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getDeclaredConstructors().getName(): " + reflectConstructor.getName());
                    System.out.println("More variables here, to be declared later");
                    System.out.println();
                    System.out.println();
                }
            }

            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getPackage(): " + platformManagedObject.getPackage());
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getPackage().getName(): " + platformManagedObject.getPackage().getName());
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getPackage().toString(): " + platformManagedObject.getPackage().toString());
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getPackage().getImplementationVersion(): " + platformManagedObject.getPackage().getImplementationVersion());
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getPackage().hashCode(): " + platformManagedObject.getPackage().hashCode());
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getPackage().getImplementationTitle(): " + platformManagedObject.getPackage().getImplementationTitle());
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getPackage().getImplementationVendor(): " + platformManagedObject.getPackage().getImplementationVendor());
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getPackage().getSpecificationTitle(): " + platformManagedObject.getPackage().getSpecificationTitle());
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getPackage().getSpecificationVendor(): " + platformManagedObject.getPackage().getSpecificationVendor());
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getPackage().getSpecificationVersion(): " + platformManagedObject.getPackage().getSpecificationVersion());
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getPackage().getAnnotations(): " + Arrays.toString(platformManagedObject.getPackage().getAnnotations()));
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getPackage().getDeclaredAnnotations(): " + Arrays.toString(platformManagedObject.getPackage().getDeclaredAnnotations()));
            System.out.println("ManagementFactory.getPlatformManagementInterfaces()["+i+"].getPackage().isSealed(): " + platformManagedObject.getPackage().isSealed());
            i++;
            System.out.println("<=======End=======>");
        }


        System.out.println();System.out.println();System.out.println();
        System.out.println();System.out.println();System.out.println();
        System.out.println();System.out.println();System.out.println();


        System.out.println("==================");
        System.out.println("====== Sigar =====");
        System.out.println("==================");
        Sigar sigar = new Sigar();

        try {
            CpuInfo[] cpuInfoList = sigar.getCpuInfoList();
            System.out.println("sigar.getCpuInfoList(): " + cpuInfoList);
            System.out.println("sigar.getCpuInfoList().toString(): " + cpuInfoList.toString());
            System.out.println("sigar.getCpuInfoList().length: " + cpuInfoList.length);
            i=0;
            for (CpuInfo cpuInfo : cpuInfoList)
            {
                System.out.println("<=======Start=======>");
                System.out.println("sigar.getCpuInfoList()["+i+"].getModel(): " + cpuInfo.getModel());
                System.out.println("sigar.getCpuInfoList()["+i+"].getMhz(): " + cpuInfo.getMhz());
                System.out.println("sigar.getCpuInfoList()["+i+"].getCacheSize(): " + cpuInfo.getCacheSize());
                System.out.println("sigar.getCpuInfoList()["+i+"].getTotalCores(): " + cpuInfo.getTotalCores());
                System.out.println("sigar.getCpuInfoList()["+i+"].getTotalSockets(): " + cpuInfo.getTotalSockets());
                System.out.println("sigar.getCpuInfoList()["+i+"].getCoresPerSocket(): " + cpuInfo.getCoresPerSocket());
                System.out.println("sigar.getCpuInfoList()["+i+"].getVendor(): " + cpuInfo.getVendor());
                System.out.println("sigar.getCpuInfoList()["+i+"].toMap(): " + cpuInfo.toMap());
                i++;
                cpuInfo.toMap().forEach((key,value)-> System.out.println(key+" : "+value));
                System.out.println("<=======End=======>");
            }

            System.out.println();System.out.println();System.out.println();


            System.out.println("Understanding CPU statistics -> https://blog.appsignal.com/2018/03/06/understanding-cpu-statistics.html");
            System.out.println("These eight states are 'user' (us), 'system', (sy), 'nice' (ni), 'idle' (id), 'iowait' (wa), 'hardware interrupt' (hi), 'software interrupt' (si), and 'steal' (st).");
            System.out.println("Of these eight, 'system', 'user' and 'idle' are the main states the CPU can be in.");
            System.out.println();
            System.out.println("Linux Tutorials: mpstat commands examples -> https://www.devopsschool.com/blog/20-mpstat-command-examples-in-linux-unix/");
            System.out.println();

            System.out.println("sigar.getCpu().toString(): " + sigar.getCpu().toString());
            System.out.println("sigar.getCpu().getIdle(): " + sigar.getCpu().getIdle());
            System.out.println("sigar.getCpu().getTotal(): " + sigar.getCpu().getTotal());
            System.out.println("sigar.getCpu().getIrq(): " + sigar.getCpu().getIrq());
            System.out.println("sigar.getCpu().getNice(): " + sigar.getCpu().getNice());
            System.out.println("sigar.getCpu().getSoftIrq(): " + sigar.getCpu().getSoftIrq());
            System.out.println("sigar.getCpu().getStolen(): " + sigar.getCpu().getStolen());
            System.out.println("sigar.getCpu().getSys(): " + sigar.getCpu().getSys());
            System.out.println("sigar.getCpu().getUser(): " + sigar.getCpu().getUser());
            System.out.println("sigar.getCpu().getWait(): " + sigar.getCpu().getWait());
            System.out.println("sigar.getCpu().toMap(): " + sigar.getCpu().toMap());
            sigar.getCpu().toMap().forEach((key,value)-> System.out.println(key+" : "+value));

            System.out.println();System.out.println();System.out.println();


            System.out.println("sigar.getCpuList(): " + sigar.getCpuList());
            System.out.println("sigar.getCpuList().toString(): " + sigar.getCpuList().toString());
            System.out.println("sigar.getCpuList().length: " + sigar.getCpuList().length);
            System.out.println("sigar.getCpuList(): " + Arrays.toString(sigar.getCpuList()));
            i=0;
            for (Cpu cpu : sigar.getCpuList())
            {
                System.out.println("<=======Start=======>");
                System.out.println("sigar.getCpuList()["+i+"].getWait(): " + cpu.getWait());
                System.out.println("sigar.getCpuList()["+i+"].toString(): " + cpu.toString());
                System.out.println("sigar.getCpuList()["+i+"].getUser(): " + cpu.getUser());
                System.out.println("sigar.getCpuList()["+i+"].getSys(): " + cpu.getSys());
                System.out.println("sigar.getCpuList()["+i+"].getNice(): " + cpu.getNice());
                System.out.println("sigar.getCpuList()["+i+"].getStolen(): " + cpu.getStolen());
                System.out.println("sigar.getCpuList()["+i+"].getIrq(): " + cpu.getIrq());
                System.out.println("sigar.getCpuList()["+i+"].getSoftIrq(): " + cpu.getSoftIrq());
                System.out.println("sigar.getCpuList()["+i+"].getTotal(): " + cpu.getTotal());
                System.out.println("sigar.getCpuList()["+i+"].getIdle(): " + cpu.getIdle());
                i++;
                cpu.toMap().forEach((key,value)->System.out.println(key+" : "+value));
                System.out.println("<=======End=======>");
            }

            System.out.println();System.out.println();System.out.println();

            System.out.println("sigar.getCpuPerc(): " + sigar.getCpuPerc());
            System.out.println("sigar.getCpuPerc().toString(): " + sigar.getCpuPerc().toString());
            System.out.println("sigar.getCpuPerc().getIdle(): " + sigar.getCpuPerc().getIdle());
            System.out.println("sigar.getCpuPerc().getNice(): " + sigar.getCpuPerc().getNice());
            System.out.println("sigar.getCpuPerc().getIrq(): " + sigar.getCpuPerc().getIrq());
            System.out.println("sigar.getCpuPerc().getSoftIrq(): " + sigar.getCpuPerc().getSoftIrq());
            System.out.println("sigar.getCpuPerc().getCombined(): " + sigar.getCpuPerc().getCombined());
            System.out.println("sigar.getCpuPerc().getStolen(): " + sigar.getCpuPerc().getStolen());
            System.out.println("sigar.getCpuPerc().getSys(): " + sigar.getCpuPerc().getSys());
            System.out.println("sigar.getCpuPerc().getUser(): " + sigar.getCpuPerc().getUser());
            System.out.println("sigar.getCpuPerc().getWait(): " + sigar.getCpuPerc().getWait());

            System.out.println();System.out.println();System.out.println();

            System.out.println("sigar.getCpuPercList(): " + sigar.getCpuPercList());
            System.out.println("sigar.getCpuPercList().toString(): " + sigar.getCpuPercList().toString());
            System.out.println("sigar.getCpuPercList().length: " + sigar.getCpuPercList().length);
            System.out.println("sigar.getCpuPercList(): " + Arrays.toString(sigar.getCpuPercList()));
            i=0;
            for(CpuPerc cpuPerc : sigar.getCpuPercList())
            {
                System.out.println("<=======Start=======>");
                System.out.println("sigar.getCpuPercList()["+i+"].toString(): " + cpuPerc.toString());
                System.out.println("sigar.getCpuPercList()["+i+"].getIdle(): " + cpuPerc.getIdle());
                System.out.println("sigar.getCpuPercList()["+i+"].getNice(): " + cpuPerc.getNice());
                System.out.println("sigar.getCpuPercList()["+i+"].getIrq(): " + cpuPerc.getIrq());
                System.out.println("sigar.getCpuPercList()["+i+"].getSoftIrq(): " + cpuPerc.getSoftIrq());
                System.out.println("sigar.getCpuPercList()["+i+"].getCombined(): " + cpuPerc.getCombined());
                System.out.println("sigar.getCpuPercList()["+i+"].getStolen(): " + cpuPerc.getStolen());
                System.out.println("sigar.getCpuPercList()["+i+"].getSys(): " + cpuPerc.getSys());
                System.out.println("sigar.getCpuPercList()["+i+"].getUser(): " + cpuPerc.getUser());
                System.out.println("sigar.getCpuPercList()["+i+"].getWait(): " + cpuPerc.getWait());
                i++;
                System.out.println("<=======End=======>");
            }

            System.out.println();System.out.println();System.out.println();

            System.out.println("sigar.getThreadCpu(): " + sigar.getThreadCpu());
            System.out.println("sigar.getThreadCpu().toString(): " + sigar.getThreadCpu().toString());
            System.out.println("sigar.getThreadCpu().getSys(): " + sigar.getThreadCpu().getSys());
            System.out.println("sigar.getThreadCpu().getTotal(): " + sigar.getThreadCpu().getTotal());
            System.out.println("sigar.getThreadCpu().getUser(): " + sigar.getThreadCpu().getUser());
            System.out.println("sigar.getThreadCpu().toMap(): " + sigar.getThreadCpu().toMap());
            sigar.getThreadCpu().toMap().forEach((key,value)-> System.out.println(key+" : "+value));

            System.out.println();System.out.println();System.out.println();

            System.out.println("sigar.getFileSystemList(): " + sigar.getFileSystemList());
            System.out.println("sigar.getFileSystemList().toString(): " + sigar.getFileSystemList().toString());
            System.out.println("sigar.getFileSystemList().length: " + sigar.getFileSystemList().length);
            i=0;
            for(FileSystem fileSystem : sigar.getFileSystemList())
            {
                System.out.println("<=======Start=======>");
                System.out.println("sigar.getFileSystemList()["+i+"].getDevName(): " + fileSystem.getDevName());
                System.out.println("sigar.getFileSystemList()["+i+"].getTypeName(): " + fileSystem.getTypeName());
                System.out.println("sigar.getFileSystemList()["+i+"].toString(): " + fileSystem.toString());
                System.out.println("sigar.getFileSystemList()["+i+"].getType(): " + fileSystem.getType());
                System.out.println("sigar.getFileSystemList()["+i+"].getDirName(): " + fileSystem.getDirName());
                System.out.println("sigar.getFileSystemList()["+i+"].getOptions(): " + fileSystem.getOptions());
                System.out.println("sigar.getFileSystemList()["+i+"].getSysTypeName(): " + fileSystem.getSysTypeName());
                System.out.println("sigar.getFileSystemList()["+i+"].getFlags(): " + fileSystem.getFlags());
                System.out.println("sigar.getFileSystemList()["+i+"].toMap(): " + fileSystem.toMap());
                i++;
                fileSystem.toMap().forEach((key,value)-> System.out.println(key+" : "+value));
                System.out.println("<=======End=======>");
            }

            System.out.println();System.out.println();System.out.println();

            System.out.println("sigar.getFileSystemMap(): " + sigar.getFileSystemMap());
            System.out.println("sigar.getFileSystemMap().toString(): " + sigar.getFileSystemMap().toString());
            sigar.getFileSystemMap().forEach((key,value)-> System.out.println(key+" : "+value));

            System.out.println();System.out.println();System.out.println();

            System.out.println("sigar.getMem(): " + sigar.getMem());
            System.out.println("sigar.getMem().toString(): " + sigar.getMem().toString());
            System.out.println("sigar.getMem().getUsed(): " + sigar.getMem().getUsed());
            System.out.println("sigar.getMem().getTotal(): " + sigar.getMem().getTotal());
            System.out.println("sigar.getMem().getActualFree(): " + sigar.getMem().getActualFree());
            System.out.println("sigar.getMem().getActualUsed(): " + sigar.getMem().getActualUsed());
            System.out.println("sigar.getMem().getFree(): " + sigar.getMem().getFree());
            System.out.println("sigar.getMem().getFreePercent(): " + sigar.getMem().getFreePercent());
            System.out.println("sigar.getMem().getRam(): " + sigar.getMem().getRam());
            System.out.println("sigar.getMem().getUsedPercent(): " + sigar.getMem().getUsedPercent());
            System.out.println("sigar.getMem().toMap(): " + sigar.getMem().toMap());
            sigar.getMem().toMap().forEach((key,value)-> System.out.println(key+" : "+value));

            System.out.println();System.out.println();System.out.println();

            System.out.println("sigar.getNativeLibrary(): " + sigar.getNativeLibrary());
            System.out.println("sigar.getNativeLibrary().getName(): " + sigar.getNativeLibrary().getName());
            System.out.println("sigar.getNativeLibrary().getAbsolutePath(): " + sigar.getNativeLibrary().getAbsolutePath());
            System.out.println("sigar.getNativeLibrary().toString(): " + sigar.getNativeLibrary().toString());
            System.out.println("sigar.getNativeLibrary().getPath(): " + sigar.getNativeLibrary().getPath());
            System.out.println("sigar.getNativeLibrary().exists(): " + sigar.getNativeLibrary().exists());
            System.out.println("sigar.getNativeLibrary().getCanonicalFile(): " + sigar.getNativeLibrary().getCanonicalFile());
            System.out.println("sigar.getNativeLibrary().getCanonicalPath(): " + sigar.getNativeLibrary().getCanonicalPath());
            System.out.println("sigar.getNativeLibrary().getParent(): " + sigar.getNativeLibrary().getParent());
            System.out.println("sigar.getNativeLibrary().getParentFile(): " + sigar.getNativeLibrary().getParentFile());
            System.out.println("sigar.getNativeLibrary().canExecute(): " + sigar.getNativeLibrary().canExecute());
            System.out.println("sigar.getNativeLibrary().canRead(): " + sigar.getNativeLibrary().canRead());
            System.out.println("sigar.getNativeLibrary().canWrite(): " + sigar.getNativeLibrary().canWrite());
            System.out.println("sigar.getNativeLibrary().getAbsoluteFile(): " + sigar.getNativeLibrary().getAbsoluteFile());
            System.out.println("sigar.getNativeLibrary().createNewFile(): " + sigar.getNativeLibrary().createNewFile());
            System.out.println("sigar.getNativeLibrary().delete(): " + sigar.getNativeLibrary().delete());
            System.out.println("sigar.getNativeLibrary().getFreeSpace(): " + sigar.getNativeLibrary().getFreeSpace());
            System.out.println("sigar.getNativeLibrary().getTotalSpace(): " + sigar.getNativeLibrary().getTotalSpace());
            System.out.println("sigar.getNativeLibrary().getUsableSpace(): " + sigar.getNativeLibrary().getUsableSpace());
            System.out.println("sigar.getNativeLibrary().isAbsolute(): " + sigar.getNativeLibrary().isAbsolute());
            System.out.println("sigar.getNativeLibrary().isDirectory(): " + sigar.getNativeLibrary().isDirectory());
            System.out.println("sigar.getNativeLibrary().isFile(): " + sigar.getNativeLibrary().isFile());
            System.out.println("sigar.getNativeLibrary().isHidden(): " + sigar.getNativeLibrary().isHidden());
            System.out.println("sigar.getNativeLibrary().list(): " + Arrays.toString(sigar.getNativeLibrary().list()));
            System.out.println("sigar.getNativeLibrary().listFiles(): " + Arrays.toString(sigar.getNativeLibrary().listFiles()));
            System.out.println("sigar.getNativeLibrary().toPath(): " + sigar.getNativeLibrary().toPath());
            System.out.println("sigar.getNativeLibrary().toPath().toUri(): " + sigar.getNativeLibrary().toPath().toUri());
            System.out.println("sigar.getNativeLibrary().toURI(): " + sigar.getNativeLibrary().toURI());
            System.out.println("sigar.getNativeLibrary().toURI().toURL(): " + sigar.getNativeLibrary().toURI().toURL());

            System.out.println();System.out.println();System.out.println();

            System.out.println("sigar.getNetInfo(): " + sigar.getNetInfo());
            System.out.println("sigar.getNetInfo().toString(): " + sigar.getNetInfo().toString());
            System.out.println("sigar.getNetInfo().getDefaultGateway(): " + sigar.getNetInfo().getDefaultGateway());
            System.out.println("sigar.getNetInfo().getDomainName(): " + sigar.getNetInfo().getDomainName());
            System.out.println("sigar.getNetInfo().getHostName(): " + sigar.getNetInfo().getHostName());
            System.out.println("sigar.getNetInfo().getPrimaryDns(): " + sigar.getNetInfo().getPrimaryDns());
            System.out.println("sigar.getNetInfo().getSecondaryDns(): " + sigar.getNetInfo().getSecondaryDns());
            System.out.println("sigar.getNetInfo().toMap(): " + sigar.getNetInfo().toMap());
            sigar.getNetInfo().toMap().forEach((key,value)-> System.out.println(key+" : "+value));

            System.out.println();System.out.println();System.out.println();

            System.out.println("sigar.getNetInterfaceConfig(): " + sigar.getNetInterfaceConfig());
            System.out.println("sigar.getNetInterfaceConfig().getName(): " + sigar.getNetInterfaceConfig().getName());
            System.out.println("sigar.getNetInterfaceConfig().getDescription(): " + sigar.getNetInterfaceConfig().getDescription());
            System.out.println("sigar.getNetInterfaceConfig().toString(): " + sigar.getNetInterfaceConfig().toString());
            System.out.println("sigar.getNetInterfaceConfig().getType(): " + sigar.getNetInterfaceConfig().getType());
            System.out.println("sigar.getNetInterfaceConfig().getAddress(): " + sigar.getNetInterfaceConfig().getAddress());
            System.out.println("sigar.getNetInterfaceConfig().getBroadcast(): " + sigar.getNetInterfaceConfig().getBroadcast());
            System.out.println("sigar.getNetInterfaceConfig().getDestination(): " + sigar.getNetInterfaceConfig().getDestination());
            System.out.println("sigar.getNetInterfaceConfig().getHwaddr(): " + sigar.getNetInterfaceConfig().getHwaddr());
            System.out.println("sigar.getNetInterfaceConfig().getNetmask(): " + sigar.getNetInterfaceConfig().getNetmask());
            System.out.println("sigar.getNetInterfaceConfig().getFlags(): " + sigar.getNetInterfaceConfig().getFlags());
            System.out.println("sigar.getNetInterfaceConfig().getMetric(): " + sigar.getNetInterfaceConfig().getMetric());
            System.out.println("sigar.getNetInterfaceConfig().getMtu(): " + sigar.getNetInterfaceConfig().getMtu());
            sigar.getNetInterfaceConfig().toMap().forEach((key,value)-> System.out.println(key+" : "+value));

            System.out.println();System.out.println();System.out.println();

            System.out.println("sigar.getNetInterfaceList(): " + Arrays.toString(sigar.getNetInterfaceList()));

            System.out.println();System.out.println();System.out.println();

            System.out.println("sigar.getNetRouteList(): " + sigar.getNetRouteList());
            System.out.println("sigar.getNetRouteList().toString(): " + sigar.getNetRouteList().toString());
            System.out.println("sigar.getNetRouteList().length: " + sigar.getNetRouteList().length);
            i=0;
            for(NetRoute netRoute : sigar.getNetRouteList())
            {
                System.out.println("<=======Start=======>");
                System.out.println("sigar.getNetRouteList()["+i+"].getMtu(): " + netRoute.getMtu());
                System.out.println("sigar.getNetRouteList()["+i+"].toString(): " + netRoute.toString());
                System.out.println("sigar.getNetRouteList()["+i+"].getDestination(): " + netRoute.getDestination());
                System.out.println("sigar.getNetRouteList()["+i+"].getFlags(): " + netRoute.getFlags());
                System.out.println("sigar.getNetRouteList()["+i+"].getGateway(): " + netRoute.getGateway());
                System.out.println("sigar.getNetRouteList()["+i+"].getIfname(): " + netRoute.getIfname());
                System.out.println("sigar.getNetRouteList()["+i+"].getMask(): " + netRoute.getMask());
                System.out.println("sigar.getNetRouteList()["+i+"].getIrtt(): " + netRoute.getIrtt());
                System.out.println("sigar.getNetRouteList()["+i+"].getMetric(): " + netRoute.getMetric());
                System.out.println("sigar.getNetRouteList()["+i+"].getRefcnt(): " + netRoute.getRefcnt());
                System.out.println("sigar.getNetRouteList()["+i+"].getUse(): " + netRoute.getUse());
                System.out.println("sigar.getNetRouteList()["+i+"].getWindow(): " + netRoute.getWindow());
                System.out.println("sigar.getNetRouteList()["+i+"].toMap(): " + netRoute.toMap());
                netRoute.toMap().forEach((key,value)-> System.out.println(key+" : "+value));
                i++;
                System.out.println("<=======End=======>");
            }

            System.out.println();System.out.println();System.out.println();

            System.out.println("sigar.getFQDN(): " + sigar.getFQDN());
            System.out.println("sigar.getLoadAverage(): " + Arrays.toString(sigar.getLoadAverage()));
            System.out.println("sigar.getPid(): " + sigar.getPid());
            System.out.println("sigar.getWhoList(): " + Arrays.toString(sigar.getWhoList()));

            System.out.println();System.out.println();System.out.println();

            System.out.println("sigar.getNetStat(): " + sigar.getNetStat());
            System.out.println("sigar.getNetStat().toString(): " + sigar.getNetStat().toString());
            System.out.println("sigar.getNetStat().getAllInboundTotal(): " + sigar.getNetStat().getAllInboundTotal());
            System.out.println("sigar.getNetStat().getAllOutboundTotal(): " + sigar.getNetStat().getAllOutboundTotal());
            System.out.println("sigar.getNetStat().getTcpInboundTotal(): " + sigar.getNetStat().getTcpInboundTotal());
            System.out.println("sigar.getNetStat().getTcpOutboundTotal(): " + sigar.getNetStat().getTcpOutboundTotal());
            System.out.println("sigar.getNetStat().getTcpBound(): " + sigar.getNetStat().getTcpBound());
            System.out.println("sigar.getNetStat().getTcpClose(): " + sigar.getNetStat().getTcpClose());
            System.out.println("sigar.getNetStat().getTcpCloseWait(): " + sigar.getNetStat().getTcpCloseWait());
            System.out.println("sigar.getNetStat().getTcpClosing(): " + sigar.getNetStat().getTcpClosing());
            System.out.println("sigar.getNetStat().getTcpEstablished(): " + sigar.getNetStat().getTcpEstablished());
            System.out.println("sigar.getNetStat().getTcpFinWait1(): " + sigar.getNetStat().getTcpFinWait1());
            System.out.println("sigar.getNetStat().getTcpFinWait2(): " + sigar.getNetStat().getTcpFinWait2());
            System.out.println("sigar.getNetStat().getTcpIdle(): " + sigar.getNetStat().getTcpIdle());
            System.out.println("sigar.getNetStat().getTcpLastAck(): " + sigar.getNetStat().getTcpLastAck());
            System.out.println("sigar.getNetStat().getTcpListen(): " + sigar.getNetStat().getTcpListen());
            System.out.println("sigar.getNetStat().getTcpStates(): " + Arrays.toString(sigar.getNetStat().getTcpStates()));
            System.out.println("sigar.getNetStat().getTcpSynRecv(): " + sigar.getNetStat().getTcpSynRecv());
            System.out.println("sigar.getNetStat().getTcpSynSent(): " + sigar.getNetStat().getTcpSynSent());
            System.out.println("sigar.getNetStat().getTcpTimeWait: " + sigar.getNetStat().getTcpTimeWait());

            System.out.println();System.out.println();System.out.println();

            /*
            System.out.println("sigar.getNfsClientV2(): " + sigar.getNfsClientV2());
            System.out.println("sigar.getNfsClientV2().getRoot(): " + sigar.getNfsClientV2().getRoot());
            System.out.println("sigar.getNfsClientV2().getWritecache(): " + sigar.getNfsClientV2().getWritecache());
            System.out.println("sigar.getNfsClientV2().toString(): " + sigar.getNfsClientV2().toString());
            System.out.println("sigar.getNfsClientV2().getCreate(): " + sigar.getNfsClientV2().getCreate());
            System.out.println("sigar.getNfsClientV2().getFsstat(): " + sigar.getNfsClientV2().getFsstat());
            System.out.println("sigar.getNfsClientV2().getGetattr(): " + sigar.getNfsClientV2().getGetattr());
            System.out.println("sigar.getNfsClientV2().getLink(): " + sigar.getNfsClientV2().getLink());
            System.out.println("sigar.getNfsClientV2().getLookup(): " + sigar.getNfsClientV2().getLookup());
            System.out.println("sigar.getNfsClientV2().getMkdir(): " + sigar.getNfsClientV2().getMkdir());
            System.out.println("sigar.getNfsClientV2().getNull(): " + sigar.getNfsClientV2().getNull());
            System.out.println("sigar.getNfsClientV2().getRead(): " + sigar.getNfsClientV2().getRead());
            System.out.println("sigar.getNfsClientV2().getReaddir(): " + sigar.getNfsClientV2().getReaddir());
            System.out.println("sigar.getNfsClientV2().getReadlink(): " + sigar.getNfsClientV2().getReadlink());
            System.out.println("sigar.getNfsClientV2().getRemove(): " + sigar.getNfsClientV2().getRemove());
            System.out.println("sigar.getNfsClientV2().getRename(): " + sigar.getNfsClientV2().getRename());
            System.out.println("sigar.getNfsClientV2().getRmdir(): " + sigar.getNfsClientV2().getRmdir());
            System.out.println("sigar.getNfsClientV2().getSetattr(): " + sigar.getNfsClientV2().getSetattr());
            System.out.println("sigar.getNfsClientV2().getSymlink(): " + sigar.getNfsClientV2().getSymlink());
            System.out.println("sigar.getNfsClientV2().getWrite(): " + sigar.getNfsClientV2().getWrite());
            System.out.println("sigar.getNfsClientV2().toMap(): " + sigar.getNfsClientV2().toMap());
            sigar.getNfsServerV2().toMap().forEach((key,value)-> System.out.println(key+" : "+value));

            System.out.println();System.out.println();System.out.println();

            System.out.println("sigar.getNfsClientV3(): " + sigar.getNfsClientV3());
            System.out.println("sigar.getNfsClientV3().toString(): " + sigar.getNfsClientV3().toString());
            System.out.println("sigar.getNfsClientV3().getAccess(): " + sigar.getNfsClientV3().getAccess());
            System.out.println("sigar.getNfsClientV3().getCommit(): " + sigar.getNfsClientV3().getCommit());
            System.out.println("sigar.getNfsClientV3().getCreate(): " + sigar.getNfsClientV3().getCreate());
            System.out.println("sigar.getNfsClientV3().getFsinfo(): " + sigar.getNfsClientV3().getFsinfo());
            System.out.println("sigar.getNfsClientV3().getFsstat(): " + sigar.getNfsClientV3().getFsstat());
            System.out.println("sigar.getNfsClientV3().getGetattr(): " + sigar.getNfsClientV3().getGetattr());
            System.out.println("sigar.getNfsClientV3().getLink(): " + sigar.getNfsClientV3().getLink());
            System.out.println("sigar.getNfsClientV3().getLookup(): " + sigar.getNfsClientV3().getLookup());
            System.out.println("sigar.getNfsClientV3().getMkdir(): " + sigar.getNfsClientV3().getMkdir());
            System.out.println("sigar.getNfsClientV3().getMknod(): " + sigar.getNfsClientV3().getMknod());
            System.out.println("sigar.getNfsClientV3().getNull(): " + sigar.getNfsClientV3().getNull());
            System.out.println("sigar.getNfsClientV3().getPathconf(): " + sigar.getNfsClientV3().getPathconf());
            System.out.println("sigar.getNfsClientV3().getRead(): " + sigar.getNfsClientV3().getRead());
            System.out.println("sigar.getNfsClientV3().getReaddir(): " + sigar.getNfsClientV3().getReaddir());
            System.out.println("sigar.getNfsClientV3().getReaddirplus(): " + sigar.getNfsClientV3().getReaddirplus());
            System.out.println("sigar.getNfsClientV3().getReadlink(): " + sigar.getNfsClientV3().getReadlink());
            System.out.println("sigar.getNfsClientV3().getRemove(): " + sigar.getNfsClientV3().getRemove());
            System.out.println("sigar.getNfsClientV3().getRename(): " + sigar.getNfsClientV3().getRename());
            System.out.println("sigar.getNfsClientV3().getRmdir(): " + sigar.getNfsClientV3().getRmdir());
            System.out.println("sigar.getNfsClientV3().getSetattr(): " + sigar.getNfsClientV3().getSetattr());
            System.out.println("sigar.getNfsClientV3().getSymlink(): " + sigar.getNfsClientV3().getSymlink());
            System.out.println("sigar.getNfsClientV3().getWrite(): " + sigar.getNfsClientV3().getWrite());
            System.out.println("sigar.getNfsClientV3().toMap(): " + sigar.getNfsClientV3().toMap());
            sigar.getNfsClientV3().toMap().forEach((key,value)-> System.out.println(key+" : "+value));

            System.out.println();System.out.println();System.out.println();

            System.out.println("sigar.getNfsServerV2(): " + sigar.getNfsServerV2());
            System.out.println("sigar.getNfsServerV2().getRoot(): " + sigar.getNfsServerV2().getRoot());
            System.out.println("sigar.getNfsServerV2().getWritecache(): " + sigar.getNfsServerV2().getWritecache());
            System.out.println("sigar.getNfsServerV2().toString(): " + sigar.getNfsServerV2().toString());
            System.out.println("sigar.getNfsServerV2().getCreate(): " + sigar.getNfsServerV2().getCreate());
            System.out.println("sigar.getNfsServerV2().getFsstat(): " + sigar.getNfsServerV2().getFsstat());
            System.out.println("sigar.getNfsServerV2().getGetattr(): " + sigar.getNfsServerV2().getGetattr());
            System.out.println("sigar.getNfsServerV2().getLink(): " + sigar.getNfsServerV2().getLink());
            System.out.println("sigar.getNfsServerV2().getLookup(): " + sigar.getNfsServerV2().getLookup());
            System.out.println("sigar.getNfsServerV2().getMkdir(): " + sigar.getNfsServerV2().getMkdir());
            System.out.println("sigar.getNfsServerV2().getNull(): " + sigar.getNfsServerV2().getNull());
            System.out.println("sigar.getNfsServerV2().getRead(): " + sigar.getNfsServerV2().getRead());
            System.out.println("sigar.getNfsServerV2().getReaddir(): " + sigar.getNfsServerV2().getReaddir());
            System.out.println("sigar.getNfsServerV2().getReadlink(): " + sigar.getNfsServerV2().getReadlink());
            System.out.println("sigar.getNfsServerV2().getRemove(): " + sigar.getNfsServerV2().getRemove());
            System.out.println("sigar.getNfsServerV2().getRename(): " + sigar.getNfsServerV2().getRename());
            System.out.println("sigar.getNfsServerV2().getRmdir(): " + sigar.getNfsServerV2().getRmdir());
            System.out.println("sigar.getNfsServerV2().getSetattr(): " + sigar.getNfsServerV2().getSetattr());
            System.out.println("sigar.getNfsServerV2().getSymlink(): " + sigar.getNfsServerV2().getSymlink());
            System.out.println("sigar.getNfsServerV2().getWrite(): " + sigar.getNfsServerV2().getWrite());
            System.out.println("sigar.getNfsServerV2().toMap(): " + sigar.getNfsServerV2().toMap());
            sigar.getNfsServerV2().toMap().forEach((key,value)-> System.out.println(key+" : "+value));

            System.out.println();System.out.println();System.out.println();

            System.out.println("sigar.getNfsServerV3(): " + sigar.getNfsServerV3());
            System.out.println("sigar.getNfsServerV3().toString(): " + sigar.getNfsServerV3().toString());
            System.out.println("sigar.getNfsServerV3().getAccess(): " + sigar.getNfsServerV3().getAccess());
            System.out.println("sigar.getNfsServerV3().getCommit(): " + sigar.getNfsServerV3().getCommit());
            System.out.println("sigar.getNfsServerV3().getCreate(): " + sigar.getNfsServerV3().getCreate());
            System.out.println("sigar.getNfsServerV3().getFsinfo(): " + sigar.getNfsServerV3().getFsinfo());
            System.out.println("sigar.getNfsServerV3().getFsstat(): " + sigar.getNfsServerV3().getFsstat());
            System.out.println("sigar.getNfsServerV3().getGetattr(): " + sigar.getNfsServerV3().getGetattr());
            System.out.println("sigar.getNfsServerV3().getLink(): " + sigar.getNfsServerV3().getLink());
            System.out.println("sigar.getNfsServerV3().getLookup(): " + sigar.getNfsServerV3().getLookup());
            System.out.println("sigar.getNfsServerV3().getMkdir(): " + sigar.getNfsServerV3().getMkdir());
            System.out.println("sigar.getNfsServerV3().getMknod(): " + sigar.getNfsServerV3().getMknod());
            System.out.println("sigar.getNfsServerV3().getNull(): " + sigar.getNfsServerV3().getNull());
            System.out.println("sigar.getNfsServerV3().getPathconf(): " + sigar.getNfsServerV3().getPathconf());
            System.out.println("sigar.getNfsServerV3().getRead(): " + sigar.getNfsServerV3().getRead());
            System.out.println("sigar.getNfsServerV3().getReaddir(): " + sigar.getNfsServerV3().getReaddir());
            System.out.println("sigar.getNfsServerV3().getReaddirplus(): " + sigar.getNfsServerV3().getReaddirplus());
            System.out.println("sigar.getNfsServerV3().getReadlink(): " + sigar.getNfsServerV3().getReadlink());
            System.out.println("sigar.getNfsServerV3().getRemove(): " + sigar.getNfsServerV3().getRemove());
            System.out.println("sigar.getNfsServerV3().getRename(): " + sigar.getNfsServerV3().getRename());
            System.out.println("sigar.getNfsServerV3().getRmdir(): " + sigar.getNfsServerV3().getRmdir());
            System.out.println("sigar.getNfsServerV3().getSetattr(): " + sigar.getNfsServerV3().getSetattr());
            System.out.println("sigar.getNfsServerV3().getSymlink(): " + sigar.getNfsServerV3().getSymlink());
            System.out.println("sigar.getNfsServerV3().getWrite(): " + sigar.getNfsServerV3().getWrite());
            System.out.println("sigar.getNfsServerV3().toMap(): " + sigar.getNfsServerV3().toMap());
            sigar.getNfsServerV3().toMap().forEach((key,value)-> System.out.println(key+" : "+value));

            System.out.println();System.out.println();System.out.println();*/

            System.out.println("sigar.getProcList(): " + Arrays.toString(sigar.getProcList()));

            System.out.println();System.out.println();System.out.println();

            System.out.println("sigar.getProcStat(): " + sigar.getProcStat());
            System.out.println("sigar.getProcStat().toString(): " + sigar.getProcStat().toString());
            System.out.println("sigar.getProcStat().getIdle(): " + sigar.getProcStat().getIdle());
            System.out.println("sigar.getProcStat().getTotal(): " + sigar.getProcStat().getTotal());
            System.out.println("sigar.getProcStat().getRunning(): " + sigar.getProcStat().getRunning());
            System.out.println("sigar.getProcStat().getSleeping(): " + sigar.getProcStat().getSleeping());
            System.out.println("sigar.getProcStat().getStopped(): " + sigar.getProcStat().getStopped());
            System.out.println("sigar.getProcStat().getThreads(): " + sigar.getProcStat().getThreads());
            System.out.println("sigar.getProcStat().getZombie(): " + sigar.getProcStat().getZombie());
            sigar.getProcStat().toMap().forEach((key,value)-> System.out.println(key+" : "+value));

            System.out.println();System.out.println();System.out.println();

            System.out.println("sigar.getResourceLimit(): " + sigar.getResourceLimit());
            System.out.println("sigar.getResourceLimit().toString(): " + sigar.getResourceLimit().toString());
            System.out.println("sigar.getResourceLimit().getCoreCur(): " + sigar.getResourceLimit().getCoreCur());
            System.out.println("sigar.getResourceLimit().getCoreMax(): " + sigar.getResourceLimit().getCoreMax());
            System.out.println("sigar.getResourceLimit().getCpuCur(): " + sigar.getResourceLimit().getCpuCur());
            System.out.println("sigar.getResourceLimit().getCpuMax(): " + sigar.getResourceLimit().getCpuMax());
            System.out.println("sigar.getResourceLimit().getDataCur(): " + sigar.getResourceLimit().getDataCur());
            System.out.println("sigar.getResourceLimit().getDataMax(): " + sigar.getResourceLimit().getDataMax());
            System.out.println("sigar.getResourceLimit().getFileSizeCur(): " + sigar.getResourceLimit().getFileSizeCur());
            System.out.println("sigar.getResourceLimit().getFileSizeMax(): " + sigar.getResourceLimit().getFileSizeMax());
            System.out.println("sigar.getResourceLimit().getMemoryCur(): " + sigar.getResourceLimit().getMemoryCur());
            System.out.println("sigar.getResourceLimit().getMemoryMax(): " + sigar.getResourceLimit().getMemoryMax());
            System.out.println("sigar.getResourceLimit().getOpenFilesCur(): " + sigar.getResourceLimit().getOpenFilesCur());
            System.out.println("sigar.getResourceLimit().getOpenFilesMax(): " + sigar.getResourceLimit().getOpenFilesMax());
            System.out.println("sigar.getResourceLimit().getPipeSizeCur(): " + sigar.getResourceLimit().getPipeSizeCur());
            System.out.println("sigar.getResourceLimit().getPipeSizeMax(): " + sigar.getResourceLimit().getPipeSizeMax());
            System.out.println("sigar.getResourceLimit().getProcessesCur(): " + sigar.getResourceLimit().getProcessesCur());
            System.out.println("sigar.getResourceLimit().getProcessesMax(): " + sigar.getResourceLimit().getProcessesMax());
            System.out.println("sigar.getResourceLimit().getStackCur(): " + sigar.getResourceLimit().getStackCur());
            System.out.println("sigar.getResourceLimit().getStackMax(): " + sigar.getResourceLimit().getStackMax());
            System.out.println("sigar.getResourceLimit().getVirtualMemoryCur(): " + sigar.getResourceLimit().getVirtualMemoryCur());
            System.out.println("sigar.getResourceLimit().getVirtualMemoryMax(): " + sigar.getResourceLimit().getVirtualMemoryMax());
            System.out.println("sigar.getResourceLimit().toMap(): " + sigar.getResourceLimit().toMap());
            sigar.getResourceLimit().toMap().forEach((key,value)-> System.out.println(key+" : "+value));


            System.out.println();System.out.println();System.out.println();


            System.out.println("sigar.getSwap(): " + sigar.getSwap());
            System.out.println("sigar.getSwap().toString(): " + sigar.getSwap().toString());
            System.out.println("sigar.getSwap().getUsed(): " + sigar.getSwap().getUsed());
            System.out.println("sigar.getSwap().getTotal(): " + sigar.getSwap().getTotal());
            System.out.println("sigar.getSwap().getFree(): " + sigar.getSwap().getFree());
            System.out.println("sigar.getSwap().getPageIn(): " + sigar.getSwap().getPageIn());
            System.out.println("sigar.getSwap().getPageOut(): " + sigar.getSwap().getPageOut());
            System.out.println("sigar.getSwap().toMap(): " + sigar.getSwap().toMap());
            sigar.getSwap().toMap().forEach((key,value)->System.out.println(key+" : "+value));

            System.out.println();System.out.println();System.out.println();

            System.out.println("sigar.getTcp(): " + sigar.getTcp());
            System.out.println("sigar.getTcp().toString(): " + sigar.getTcp().toString());
            System.out.println("sigar.getTcp().getActiveOpens(): " + sigar.getTcp().getActiveOpens());
            System.out.println("sigar.getTcp().getAttemptFails(): " + sigar.getTcp().getAttemptFails());
            System.out.println("sigar.getTcp().getOutRsts(): " + sigar.getTcp().getOutRsts());
            System.out.println("sigar.getTcp().getCurrEstab(): " + sigar.getTcp().getCurrEstab());
            System.out.println("sigar.getTcp().getEstabResets(): " + sigar.getTcp().getEstabResets());
            System.out.println("sigar.getTcp().getInErrs(): " + sigar.getTcp().getInErrs());
            System.out.println("sigar.getTcp().getInSegs(): " + sigar.getTcp().getInSegs());
            System.out.println("sigar.getTcp().getOutSegs(): " + sigar.getTcp().getOutSegs());
            System.out.println("sigar.getTcp().getPassiveOpens(): " + sigar.getTcp().getPassiveOpens());
            System.out.println("sigar.getTcp().getRetransSegs(): " + sigar.getTcp().getRetransSegs());
            System.out.println("sigar.getTcp().toMap(): " + sigar.getTcp().toMap());
            sigar.getTcp().toMap().forEach((key,value)-> System.out.println(key+" : "+value));


            System.out.println();System.out.println();System.out.println();


            System.out.println("sigar.getUptime(): " + sigar.getUptime());
            System.out.println("sigar.getUptime().toString(): " + sigar.getUptime().toString());
            System.out.println("sigar.getUptime().getUptime(): " + sigar.getUptime().getUptime());
            System.out.println("sigar.getUptime().toMap(): " + sigar.getUptime().toMap());
            sigar.getUptime().toMap().forEach((key,value)-> System.out.println(key+" : "+value));

            System.out.println();System.out.println();System.out.println();


            // Explore other methods in Sigar for more details
        } catch (SigarException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*JSysInfo sysInfo = new JSysInfo();
        CpuInfo cpuInfo = sysInfo.getCpuInfo();

        System.out.println("CPU Model: " + cpuInfo.getModel());
        System.out.println("CPU Speed: " + cpuInfo.getClockSpeed() + " MHz");

        System.out.println();*/

        System.out.println();System.out.println();System.out.println();
        System.out.println();System.out.println();System.out.println();
        System.out.println();System.out.println();System.out.println();


        System.out.println("============================================================================================================");
        System.out.println("================================================= oshi =====================================================");
        System.out.println("oshi.SystemInfo systemInfo = new SystemInfo()");
        System.out.println("oshi.hardware.HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware()");
        System.out.println("oshi.software.os.OperatingSystem operatingSystem = systemInfo.getOperatingSystem()");
        System.out.println("============================================================================================================");


        oshi.SystemInfo systemInfo = new SystemInfo();
        oshi.hardware.HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
        oshi.software.os.OperatingSystem operatingSystem = systemInfo.getOperatingSystem();

        System.out.println("systemInfo.getHardware(): " + systemInfo.getHardware());
        System.out.println("systemInfo.getHardware().toString(): " + systemInfo.getHardware().toString());
        System.out.println("systemInfo.getHardware().getProcessor(): " + systemInfo.getHardware().getProcessor());
        System.out.println("systemInfo.getHardware().getProcessor().toString(): " + systemInfo.getHardware().getProcessor().toString());
        System.out.println("systemInfo.getHardware().getProcessor().getContextSwitches(): " + systemInfo.getHardware().getProcessor().getContextSwitches());

        System.out.println("systemInfo.getHardware().getProcessor().getPhysicalPackageCount(): " + systemInfo.getHardware().getProcessor().getPhysicalPackageCount());
        System.out.println("systemInfo.getHardware().getProcessor().getPhysicalProcessorCount(): " + systemInfo.getHardware().getProcessor().getPhysicalProcessorCount());

        System.out.println("systemInfo.getHardware().getProcessor().getPhysicalProcessors(): " + systemInfo.getHardware().getProcessor().getPhysicalProcessors());
        System.out.println("systemInfo.getHardware().getProcessor().getPhysicalProcessors().toString(): " + systemInfo.getHardware().getProcessor().getPhysicalProcessors().toString());
        System.out.println("systemInfo.getHardware().getProcessor().getPhysicalProcessors().size(): " + systemInfo.getHardware().getProcessor().getPhysicalProcessors().size());
        i=0;
        for(CentralProcessor.PhysicalProcessor centralProcessorPhysicalProcessor: systemInfo.getHardware().getProcessor().getPhysicalProcessors())
        {
            System.out.println("<=======Start=======>");
            System.out.println("systemInfo.getHardware().getProcessor().getPhysicalProcessors()["+i+"].toString(): " + centralProcessorPhysicalProcessor.toString());
            System.out.println("systemInfo.getHardware().getProcessor().getPhysicalProcessors()["+i+"].getIdString(): " + centralProcessorPhysicalProcessor.getIdString());
            System.out.println("systemInfo.getHardware().getProcessor().getPhysicalProcessors()["+i+"].getPhysicalProcessorNumber(): " + centralProcessorPhysicalProcessor.getPhysicalProcessorNumber());
            System.out.println("systemInfo.getHardware().getProcessor().getPhysicalProcessors()["+i+"].getEfficiency(): " + centralProcessorPhysicalProcessor.getEfficiency());
            System.out.println("systemInfo.getHardware().getProcessor().getPhysicalProcessors()["+i+"].getPhysicalPackageNumber(): " + centralProcessorPhysicalProcessor.getPhysicalPackageNumber());
            i++;
            System.out.println("<=======End=======>");
        }

        System.out.println("systemInfo.getHardware().getProcessor().getLogicalProcessorCount(): " + systemInfo.getHardware().getProcessor().getLogicalProcessorCount());
        System.out.println("systemInfo.getHardware().getProcessor().getLogicalProcessors(): " + systemInfo.getHardware().getProcessor().getLogicalProcessors());
        System.out.println("systemInfo.getHardware().getProcessor().getLogicalProcessors().toString(): " + systemInfo.getHardware().getProcessor().getLogicalProcessors().toString());
        System.out.println("systemInfo.getHardware().getProcessor().getLogicalProcessors().size(): " + systemInfo.getHardware().getProcessor().getLogicalProcessors().size());
        i=0;
        for(CentralProcessor.LogicalProcessor centralProcessorLogicalProcessor: systemInfo.getHardware().getProcessor().getLogicalProcessors())
        {
            System.out.println("<=======Start=======>");
            System.out.println("systemInfo.getHardware().getProcessor().getLogicalProcessors()["+i+"].toString(): " + centralProcessorLogicalProcessor.toString());
            System.out.println("systemInfo.getHardware().getProcessor().getLogicalProcessors()["+i+"].getProcessorGroup(): " + centralProcessorLogicalProcessor.getProcessorGroup());
            System.out.println("systemInfo.getHardware().getProcessor().getLogicalProcessors()["+i+"].getProcessorNumber(): " + centralProcessorLogicalProcessor.getProcessorNumber());
            System.out.println("systemInfo.getHardware().getProcessor().getLogicalProcessors()["+i+"].getNumaNode(): " + centralProcessorLogicalProcessor.getNumaNode());
            System.out.println("systemInfo.getHardware().getProcessor().getLogicalProcessors()["+i+"].getPhysicalPackageNumber(): " + centralProcessorLogicalProcessor.getPhysicalPackageNumber());
            System.out.println("systemInfo.getHardware().getProcessor().getLogicalProcessors()["+i+"].getPhysicalProcessorNumber(): " + centralProcessorLogicalProcessor.getPhysicalProcessorNumber());
            i++;
            System.out.println("<=======End=======>");
        }

        System.out.println("systemInfo.getHardware().getProcessor().getCurrentFreq(): " + Arrays.toString(systemInfo.getHardware().getProcessor().getCurrentFreq()));
        System.out.println("systemInfo.getHardware().getProcessor().getMaxFreq(): " + systemInfo.getHardware().getProcessor().getMaxFreq());
        System.out.println("systemInfo.getHardware().getProcessor().getInterrupts(): " + systemInfo.getHardware().getProcessor().getInterrupts());
        System.out.println("systemInfo.getHardware().getProcessor().getProcessorCpuLoadTicks(): " + Arrays.deepToString(systemInfo.getHardware().getProcessor().getProcessorCpuLoadTicks()));
        System.out.println("systemInfo.getHardware().getProcessor().getSystemCpuLoadTicks(): " + Arrays.toString(systemInfo.getHardware().getProcessor().getSystemCpuLoadTicks()));

        System.out.println("systemInfo.getHardware().getProcessor().getProcessorCaches(): " + systemInfo.getHardware().getProcessor().getProcessorCaches());
        System.out.println("systemInfo.getHardware().getProcessor().getProcessorCaches().toString(): " + systemInfo.getHardware().getProcessor().getProcessorCaches().toString());
        System.out.println("systemInfo.getHardware().getProcessor().getProcessorCaches().size(): " + systemInfo.getHardware().getProcessor().getProcessorCaches().size());
        i=0;
        for(CentralProcessor.ProcessorCache centralProcessorProcessorCache: systemInfo.getHardware().getProcessor().getProcessorCaches())
        {
            System.out.println("<=======Start=======>");
            System.out.println("systemInfo.getHardware().getProcessor().getProcessorCaches()["+i+"].toString(): " + centralProcessorProcessorCache.toString());
            System.out.println("systemInfo.getHardware().getProcessor().getProcessorCaches()["+i+"].getType(): " + centralProcessorProcessorCache.getType());
            System.out.println("systemInfo.getHardware().getProcessor().getProcessorCaches()["+i+"].getCacheSize(): " + centralProcessorProcessorCache.getCacheSize());
            System.out.println("systemInfo.getHardware().getProcessor().getProcessorCaches()["+i+"].getLineSize(): " + centralProcessorProcessorCache.getLineSize());
            System.out.println("systemInfo.getHardware().getProcessor().getProcessorCaches()["+i+"].getAssociativity(): " + centralProcessorProcessorCache.getAssociativity());
            System.out.println("systemInfo.getHardware().getProcessor().getProcessorCaches()["+i+"].getLevel(): " + centralProcessorProcessorCache.getLevel());
            i++;
            System.out.println("<=======End=======>");
        }

        System.out.println("systemInfo.getHardware().getProcessor().getProcessorIdentifier(): " + systemInfo.getHardware().getProcessor().getProcessorIdentifier());
        System.out.println("systemInfo.getHardware().getProcessor().getProcessorIdentifier().toString(): " + systemInfo.getHardware().getProcessor().getProcessorIdentifier().toString());
        System.out.println("systemInfo.getHardware().getProcessor().getProcessorIdentifier().getIdentifier(): " + systemInfo.getHardware().getProcessor().getProcessorIdentifier().getIdentifier());
        System.out.println("systemInfo.getHardware().getProcessor().getProcessorIdentifier().getName(): " + systemInfo.getHardware().getProcessor().getProcessorIdentifier().getName());
        System.out.println("systemInfo.getHardware().getProcessor().getProcessorIdentifier().getProcessorID(): " + systemInfo.getHardware().getProcessor().getProcessorIdentifier().getProcessorID());
        System.out.println("systemInfo.getHardware().getProcessor().getProcessorIdentifier().getModel(): " + systemInfo.getHardware().getProcessor().getProcessorIdentifier().getModel());
        System.out.println("systemInfo.getHardware().getProcessor().getProcessorIdentifier().getFamily(): " + systemInfo.getHardware().getProcessor().getProcessorIdentifier().getFamily());
        System.out.println("systemInfo.getHardware().getProcessor().getProcessorIdentifier().getMicroarchitecture(): " + systemInfo.getHardware().getProcessor().getProcessorIdentifier().getMicroarchitecture());
        System.out.println("systemInfo.getHardware().getProcessor().getProcessorIdentifier().getStepping(): " + systemInfo.getHardware().getProcessor().getProcessorIdentifier().getStepping());
        System.out.println("systemInfo.getHardware().getProcessor().getProcessorIdentifier().getVendor(): " + systemInfo.getHardware().getProcessor().getProcessorIdentifier().getVendor());
        System.out.println("systemInfo.getHardware().getProcessor().getProcessorIdentifier().getVendorFreq(): " + systemInfo.getHardware().getProcessor().getProcessorIdentifier().getVendorFreq());
        System.out.println("systemInfo.getHardware().getProcessor().getProcessorIdentifier().isCpu64bit(): " + systemInfo.getHardware().getProcessor().getProcessorIdentifier().isCpu64bit());

        System.out.println();System.out.println();System.out.println();


        System.out.println("systemInfo.getHardware().getComputerSystem(): " + systemInfo.getHardware().getComputerSystem());
        System.out.println("systemInfo.getHardware().getComputerSystem().toString(): " + systemInfo.getHardware().getComputerSystem().toString());
        System.out.println("systemInfo.getHardware().getComputerSystem().getHardwareUUID(): " + systemInfo.getHardware().getComputerSystem().getHardwareUUID());
        System.out.println("systemInfo.getHardware().getComputerSystem().getModel(): " + systemInfo.getHardware().getComputerSystem().getModel());
        System.out.println("systemInfo.getHardware().getComputerSystem().getManufacturer(): " + systemInfo.getHardware().getComputerSystem().getManufacturer());
        System.out.println("systemInfo.getHardware().getComputerSystem().getSerialNumber(): " + systemInfo.getHardware().getComputerSystem().getSerialNumber());

        System.out.println("systemInfo.getHardware().getComputerSystem().getFirmware(): " + systemInfo.getHardware().getComputerSystem().getFirmware());
        System.out.println("systemInfo.getHardware().getComputerSystem().getFirmware().toString(): " + systemInfo.getHardware().getComputerSystem().getFirmware().toString());
        System.out.println("systemInfo.getHardware().getComputerSystem().getFirmware().getName(): " + systemInfo.getHardware().getComputerSystem().getFirmware().getName());
        System.out.println("systemInfo.getHardware().getComputerSystem().getFirmware().getDescription(): " + systemInfo.getHardware().getComputerSystem().getFirmware().getDescription());
        System.out.println("systemInfo.getHardware().getComputerSystem().getFirmware().getVersion(): " + systemInfo.getHardware().getComputerSystem().getFirmware().getVersion());
        System.out.println("systemInfo.getHardware().getComputerSystem().getFirmware().getManufacturer(): " + systemInfo.getHardware().getComputerSystem().getFirmware().getManufacturer());
        System.out.println("systemInfo.getHardware().getComputerSystem().getFirmware().getReleaseDate(): " + systemInfo.getHardware().getComputerSystem().getFirmware().getReleaseDate());



        System.out.println("systemInfo.getHardware().getComputerSystem().getBaseboard(): " + systemInfo.getHardware().getComputerSystem().getBaseboard());
        System.out.println("systemInfo.getHardware().getComputerSystem().getBaseboard().toString(): " + systemInfo.getHardware().getComputerSystem().getBaseboard().toString());
        System.out.println("systemInfo.getHardware().getComputerSystem().getBaseboard().getManufacturer(): " + systemInfo.getHardware().getComputerSystem().getBaseboard().getManufacturer());
        System.out.println("systemInfo.getHardware().getComputerSystem().getBaseboard().getModel(): " + systemInfo.getHardware().getComputerSystem().getBaseboard().getModel());
        System.out.println("systemInfo.getHardware().getComputerSystem().getBaseboard().getVersion(): " + systemInfo.getHardware().getComputerSystem().getBaseboard().getVersion());
        System.out.println("systemInfo.getHardware().getComputerSystem().getBaseboard().getSerialNumber(): " + systemInfo.getHardware().getComputerSystem().getBaseboard().getSerialNumber());

        System.out.println();System.out.println();System.out.println();


        System.out.println("systemInfo.getHardware().getDiskStores(): " + systemInfo.getHardware().getDiskStores());
        System.out.println("systemInfo.getHardware().getDiskStores().toString(): " + systemInfo.getHardware().getDiskStores().toString());
        System.out.println("systemInfo.getHardware().getDiskStores().size(): " + systemInfo.getHardware().getDiskStores().size());
        i=0;
        for(oshi.hardware.HWDiskStore hwDiskStore: systemInfo.getHardware().getDiskStores())
        {
            System.out.println("<=======Start=======>");
            System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].getName(): " + hwDiskStore.getName());
            System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].toString(): " + hwDiskStore.toString());
            System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].getModel(): " + hwDiskStore.getModel());
            System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].getSerial(): " + hwDiskStore.getSerial());
            System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].getCurrentQueueLength(): " + hwDiskStore.getCurrentQueueLength());
            System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].getReadBytes(): " + hwDiskStore.getReadBytes());
            System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].getReads(): " + hwDiskStore.getReads());
            System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].getSize(): " + hwDiskStore.getSize());
            System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].getTimeStamp(): " + hwDiskStore.getTimeStamp());
            System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].getTransferTime(): " + hwDiskStore.getTransferTime());
            System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].updateAttributes(): " + hwDiskStore.updateAttributes());
            System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].getWriteBytes(): " + hwDiskStore.getWriteBytes());

            System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].getPartitions(): " + hwDiskStore.getPartitions());
            System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].getPartitions().toString(): " + hwDiskStore.getPartitions().toString());
            System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].getPartitions().size(): " + hwDiskStore.getPartitions().size());
            j=0;
            for(oshi.hardware.HWPartition hWPartition: hwDiskStore.getPartitions())
            {
                System.out.println("<=======Start=======>");
                System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].getPartitions()["+j+"].getName(): " + hWPartition.getName());
                System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].getPartitions()["+j+"].toString(): " + hWPartition.toString());
                System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].getPartitions()["+j+"].getType(): " + hWPartition.getType());
                System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].getPartitions()["+j+"].getSize(): " + hWPartition.getSize());
                System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].getPartitions()["+j+"].getUuid(): " + hWPartition.getUuid());
                System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].getPartitions()["+j+"].getIdentification(): " + hWPartition.getIdentification());
                System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].getPartitions()["+j+"].getMountPoint(): " + hWPartition.getMountPoint());
                System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].getPartitions()["+j+"].getMajor(): " + hWPartition.getMajor());
                System.out.println("systemInfo.getHardware().getDiskStores()["+i+"].getPartitions()["+j+"].getMinor(): " + hWPartition.getMinor());
                j++;
                System.out.println("<=======End=======>");
            }
            i++;
            System.out.println("<=======End=======>");
        }

        System.out.println();System.out.println();System.out.println();

        System.out.println("systemInfo.getHardware().getDisplays(): " + systemInfo.getHardware().getDisplays());
        System.out.println("systemInfo.getHardware().getDisplays().toString(): " + systemInfo.getHardware().getDisplays().toString());
        System.out.println("systemInfo.getHardware().getDisplays().size(): " + systemInfo.getHardware().getDisplays().size());
        i=0;
        for(oshi.hardware.Display display: systemInfo.getHardware().getDisplays())
        {
            System.out.println("<=======Start=======>");
            System.out.println("systemInfo.getHardware().getDisplays()["+i+"].getEdid(): " + Arrays.toString(display.getEdid()));
            i++;
            System.out.println("<=======End=======>");
        }

        System.out.println();System.out.println();System.out.println();

        System.out.println("systemInfo.getHardware().getGraphicsCards(): " + systemInfo.getHardware().getGraphicsCards());
        System.out.println("systemInfo.getHardware().getGraphicsCards().toString(): " + systemInfo.getHardware().getGraphicsCards().toString());
        System.out.println("systemInfo.getHardware().getGraphicsCards().size(): " + systemInfo.getHardware().getGraphicsCards().size());
        i=0;
        for(oshi.hardware.GraphicsCard graphicsCard : systemInfo.getHardware().getGraphicsCards())
        {
            System.out.println("<=======Start=======>");
            System.out.println("systemInfo.getHardware().getGraphicsCards()["+i+"].getName(): " + graphicsCard.getName());
            System.out.println("systemInfo.getHardware().getGraphicsCards()["+i+"].toString(): " + graphicsCard.toString());
            System.out.println("systemInfo.getHardware().getGraphicsCards()["+i+"].getVendor(): " + graphicsCard.getVendor());
            System.out.println("systemInfo.getHardware().getGraphicsCards()["+i+"].getVersionInfo(): " + graphicsCard.getVersionInfo());
            System.out.println("systemInfo.getHardware().getGraphicsCards()["+i+"].getDeviceId(): " + graphicsCard.getDeviceId());
            System.out.println("systemInfo.getHardware().getGraphicsCards()["+i+"].getVRam(): " + graphicsCard.getVRam());
            i++;
            System.out.println("<=======End=======>");
        }

        System.out.println();System.out.println();System.out.println();

        System.out.println("systemInfo.getHardware().getLogicalVolumeGroups(): " + systemInfo.getHardware().getLogicalVolumeGroups());
        System.out.println("systemInfo.getHardware().getLogicalVolumeGroups().toString(): " + systemInfo.getHardware().getLogicalVolumeGroups().toString());
        System.out.println("systemInfo.getHardware().getLogicalVolumeGroups().size(): " + systemInfo.getHardware().getLogicalVolumeGroups().size());
        i=0;
        for(LogicalVolumeGroup logicalVolumeGroup : systemInfo.getHardware().getLogicalVolumeGroups())
        {
            System.out.println("<=======Start=======>");
            System.out.println("systemInfo.getHardware().getLogicalVolumeGroups()["+i+"].getName(): " + logicalVolumeGroup.getName());
            System.out.println("systemInfo.getHardware().getLogicalVolumeGroups()["+i+"].toString(): " + logicalVolumeGroup.toString());
            System.out.println("systemInfo.getHardware().getLogicalVolumeGroups()["+i+"].getPhysicalVolumes(): " + logicalVolumeGroup.getPhysicalVolumes());
            System.out.println("systemInfo.getHardware().getLogicalVolumeGroups()["+i+"].getPhysicalVolumes().toString(): " + logicalVolumeGroup.getPhysicalVolumes().toString());
            System.out.println("systemInfo.getHardware().getLogicalVolumeGroups()["+i+"].getPhysicalVolumes().size(): " + logicalVolumeGroup.getPhysicalVolumes().size());
            j=0;
            for(String string : logicalVolumeGroup.getPhysicalVolumes())
            {
                System.out.println("systemInfo.getHardware().getLogicalVolumeGroups()["+i+"].getPhysicalVolumes()["+j+"]: " + string);
                j++;
            }
            System.out.println("systemInfo.getHardware().getLogicalVolumeGroups()["+i+"].getLogicalVolumes(): " + logicalVolumeGroup.getLogicalVolumes());
            System.out.println("systemInfo.getHardware().getLogicalVolumeGroups()["+i+"].getLogicalVolumes().toString(): " + logicalVolumeGroup.getLogicalVolumes().toString());
            System.out.println("systemInfo.getHardware().getLogicalVolumeGroups()["+i+"].getLogicalVolumes().size(): " + logicalVolumeGroup.getLogicalVolumes().size());
            logicalVolumeGroup.getLogicalVolumes().forEach((key,value)-> System.out.println(key+" : "+value));
            i++;
            System.out.println("<=======End=======>");
        }

        System.out.println();System.out.println();System.out.println();

        System.out.println("systemInfo.getHardware().getMemory(): " + systemInfo.getHardware().getMemory());
        System.out.println("systemInfo.getHardware().getMemory().toString(): " + systemInfo.getHardware().getMemory().toString());
        System.out.println("systemInfo.getHardware().getMemory().getTotal(): " + systemInfo.getHardware().getMemory().getTotal());
        System.out.println("systemInfo.getHardware().getMemory().getAvailable(): " + systemInfo.getHardware().getMemory().getAvailable());
        System.out.println("systemInfo.getHardware().getMemory().getPageSize(): " + systemInfo.getHardware().getMemory().getPageSize());

        System.out.println("systemInfo.getHardware().getMemory().getPhysicalMemory(): " + systemInfo.getHardware().getMemory().getPhysicalMemory());
        System.out.println("systemInfo.getHardware().getMemory().getPhysicalMemory().toString(): " + systemInfo.getHardware().getMemory().getPhysicalMemory().toString());
        System.out.println("systemInfo.getHardware().getMemory().getPhysicalMemory().size(): " + systemInfo.getHardware().getMemory().getPhysicalMemory().size());
        i=0;
        for(PhysicalMemory physicalMemory: systemInfo.getHardware().getMemory().getPhysicalMemory())
        {
            System.out.println("<=======Start=======>");
            System.out.println("systemInfo.getHardware().getMemory().getPhysicalMemory()["+i+"].toString(): " + physicalMemory.toString());
            System.out.println("systemInfo.getHardware().getMemory().getPhysicalMemory()["+i+"].getMemoryType(): " + physicalMemory.getMemoryType());
            System.out.println("systemInfo.getHardware().getMemory().getPhysicalMemory()["+i+"].getBankLabel(): " + physicalMemory.getBankLabel());
            System.out.println("systemInfo.getHardware().getMemory().getPhysicalMemory()["+i+"].getManufacturer(): " + physicalMemory.getManufacturer());
            System.out.println("systemInfo.getHardware().getMemory().getPhysicalMemory()["+i+"].getCapacity(): " + physicalMemory.getCapacity());
            System.out.println("systemInfo.getHardware().getMemory().getPhysicalMemory()["+i+"].getClockSpeed(): " + physicalMemory.getClockSpeed());
            System.out.println("<=======End=======>");
        }
        System.out.println();System.out.println();
        System.out.println("systemInfo.getHardware().getMemory().getVirtualMemory(): " + systemInfo.getHardware().getMemory().getVirtualMemory());
        System.out.println("systemInfo.getHardware().getMemory().getVirtualMemory().toString(): " + systemInfo.getHardware().getMemory().getVirtualMemory().toString());
        System.out.println("systemInfo.getHardware().getMemory().getVirtualMemory().getSwapTotal(): " + systemInfo.getHardware().getMemory().getVirtualMemory().getSwapTotal());
        System.out.println("systemInfo.getHardware().getMemory().getVirtualMemory().getSwapUsed(): " + systemInfo.getHardware().getMemory().getVirtualMemory().getSwapUsed());
        System.out.println("systemInfo.getHardware().getMemory().getVirtualMemory().getSwapPagesIn(): " + systemInfo.getHardware().getMemory().getVirtualMemory().getSwapPagesIn());
        System.out.println("systemInfo.getHardware().getMemory().getVirtualMemory().getSwapPagesOut(): " + systemInfo.getHardware().getMemory().getVirtualMemory().getSwapPagesOut());
        System.out.println("systemInfo.getHardware().getMemory().getVirtualMemory().getVirtualInUse(): " + systemInfo.getHardware().getMemory().getVirtualMemory().getVirtualInUse());
        System.out.println("systemInfo.getHardware().getMemory().getVirtualMemory().getVirtualMax(): " + systemInfo.getHardware().getMemory().getVirtualMemory().getVirtualMax());


        System.out.println();System.out.println();System.out.println();

        System.out.println("systemInfo.getHardware().getNetworkIFs(): " + systemInfo.getHardware().getNetworkIFs());
        System.out.println("systemInfo.getHardware().getNetworkIFs().toString(): " + systemInfo.getHardware().getNetworkIFs().toString());
        System.out.println();System.out.println();
        System.out.println("systemInfo.getHardware().getNetworkIFs().size(): " + systemInfo.getHardware().getNetworkIFs().size());
        System.out.println();

        i=0;
        for(NetworkIF networkIF: systemInfo.getHardware().getNetworkIFs())
        {
            System.out.println("<=======Start=======>");
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getName(): " + networkIF.getName());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].toString(): " + networkIF.toString());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getDisplayName(): " + networkIF.getDisplayName());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getMacaddr(): " + networkIF.getMacaddr());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getIfAlias(): " + networkIF.getIfAlias());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getBytesRecv(): " + networkIF.getBytesRecv());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getBytesSent(): " + networkIF.getBytesSent());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getCollisions(): " + networkIF.getCollisions());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getIfType(): " + networkIF.getIfType());

            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getIfOperStatus(): " + networkIF.getIfOperStatus());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getIfOperStatus().name(): " + networkIF.getIfOperStatus().name());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getIfOperStatus().toString(): " + networkIF.getIfOperStatus().toString());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getIfOperStatus().ordinal(): " + networkIF.getIfOperStatus().ordinal());

            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getIndex(): " + networkIF.getIndex());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getInDrops(): " + networkIF.getInDrops());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getInErrors(): " + networkIF.getInErrors());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getIPv4addr(): " + Arrays.toString(networkIF.getIPv4addr()));
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getIPv6addr(): " + Arrays.toString(networkIF.getIPv6addr()));
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getMTU(): " + networkIF.getMTU());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getNdisPhysicalMediumType(): " + networkIF.getNdisPhysicalMediumType());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getOutErrors(): " + networkIF.getOutErrors());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getPacketsRecv(): " + networkIF.getPacketsRecv());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getPacketsSent(): " + networkIF.getPacketsSent());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getPrefixLengths(): " + Arrays.toString(networkIF.getPrefixLengths()));
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getSpeed(): " + networkIF.getSpeed());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getSubnetMasks(): " + Arrays.toString(networkIF.getSubnetMasks()));
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].getTimeStamp(): " + networkIF.getTimeStamp());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].isConnectorPresent(): " + networkIF.isConnectorPresent());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].isKnownVmMacAddr(): " + networkIF.isKnownVmMacAddr());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].updateAttributes(): " + networkIF.updateAttributes());

            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface(): " + networkIF.queryNetworkInterface());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getName(): " + networkIF.queryNetworkInterface().getName());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().toString(): " + networkIF.queryNetworkInterface().toString());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getDisplayName(): " + networkIF.queryNetworkInterface().getDisplayName());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getHardwareAddress(): " + Arrays.toString(networkIF.queryNetworkInterface().getHardwareAddress()));
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getIndex(): " + networkIF.queryNetworkInterface().getIndex());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getMTU(): " + networkIF.queryNetworkInterface().getMTU());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().isLoopback(): " + networkIF.queryNetworkInterface().isLoopback());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().isUp(): " + networkIF.queryNetworkInterface().isUp());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().isPointToPoint(): " + networkIF.queryNetworkInterface().isPointToPoint());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().isVirtual(): " + networkIF.queryNetworkInterface().isVirtual());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().supportsMulticast(): " + networkIF.queryNetworkInterface().supportsMulticast());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().hashCode(): " + networkIF.queryNetworkInterface().hashCode());

            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInetAddresses(): " + networkIF.queryNetworkInterface().getInetAddresses());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInetAddresses().toString(): " + networkIF.queryNetworkInterface().getInetAddresses().toString());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInetAddresses().hasMoreElements(): " + networkIF.queryNetworkInterface().getInetAddresses().hasMoreElements());
            java.util.Enumeration<java.net.InetAddress> inetAddressEnum = networkIF.queryNetworkInterface().getInetAddresses();
            System.out.println();

            j=0;
            while(inetAddressEnum.hasMoreElements())
            {
                System.out.println("<=======Start=======>");
                java.net.InetAddress inetAddress = inetAddressEnum.nextElement();
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInetAddresses().nextElement()["+j+"]: " + inetAddress);
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInetAddresses().nextElement()["+j+"].getHostName(): " + inetAddress.getHostName());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInetAddresses().nextElement()["+j+"].getHostAddress(): " + inetAddress.getHostAddress());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInetAddresses().nextElement()["+j+"].toString(): " + inetAddress.toString());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInetAddresses().nextElement()["+j+"].isMulticastAddress(): " + inetAddress.isMulticastAddress());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInetAddresses().nextElement()["+j+"].isSiteLocalAddress(): " + inetAddress.isSiteLocalAddress());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInetAddresses().nextElement()["+j+"].hashCode(): " + inetAddress.hashCode());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInetAddresses().nextElement()["+j+"].getCanonicalHostName(): " + inetAddress.getCanonicalHostName());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInetAddresses().nextElement()["+j+"].getAddress(): " + Arrays.toString(inetAddress.getAddress()));
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInetAddresses().nextElement()["+j+"].isAnyLocalAddress(): " + inetAddress.isAnyLocalAddress());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInetAddresses().nextElement()["+j+"].isLoopbackAddress(): " + inetAddress.isLoopbackAddress());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInetAddresses().nextElement()["+j+"].isLinkLocalAddress(): " + inetAddress.isLinkLocalAddress());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInetAddresses().nextElement()["+j+"].isMCLinkLocal(): " + inetAddress.isMCLinkLocal());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInetAddresses().nextElement()["+j+"].isMCGlobal(): " + inetAddress.isMCGlobal());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInetAddresses().nextElement()["+j+"].isMCOrgLocal(): " + inetAddress.isMCOrgLocal());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInetAddresses().nextElement()["+j+"].isMCSiteLocal(): " + inetAddress.isMCSiteLocal());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInetAddresses().nextElement().["+j+"]isMCNodeLocal(): " + inetAddress.isMCNodeLocal());
                j++;
                System.out.println("<=======End=======>");
            }


            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses(): " + networkIF.queryNetworkInterface().getInterfaceAddresses());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses().toString(): " + networkIF.queryNetworkInterface().getInterfaceAddresses().toString());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses().size(): " + networkIF.queryNetworkInterface().getInterfaceAddresses().size());

            j=0;
            for(InterfaceAddress interfaceAddress: networkIF.queryNetworkInterface().getInterfaceAddresses())
            {
                System.out.println("<=======Start=======>");
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].toString(): " + interfaceAddress.toString());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getNetworkPrefixLength(): " + interfaceAddress.getNetworkPrefixLength());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].hashCode(): " + interfaceAddress.hashCode());

                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getAddress(): " + interfaceAddress.getAddress());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getAddress().toString(): " + interfaceAddress.getAddress().toString());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getAddress().getHostName(): " + interfaceAddress.getAddress().getHostName());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getAddress().getCanonicalHostName(): " + interfaceAddress.getAddress().getCanonicalHostName());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getAddress().getHostAddress(): " + interfaceAddress.getAddress().getHostAddress());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getAddress().getAddress(): " + Arrays.toString(interfaceAddress.getAddress().getAddress()));
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getAddress().hashCode(): " + interfaceAddress.getAddress().hashCode());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getAddress().isAnyLocalAddress(): " + interfaceAddress.getAddress().isAnyLocalAddress());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getAddress().isLinkLocalAddress(): " + interfaceAddress.getAddress().isLinkLocalAddress());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getAddress().isLoopbackAddress(): " + interfaceAddress.getAddress().isLoopbackAddress());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getAddress().isMulticastAddress(): " + interfaceAddress.getAddress().isMulticastAddress());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getAddress().isSiteLocalAddress(): " + interfaceAddress.getAddress().isSiteLocalAddress());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getAddress().isMCGlobal(): " + interfaceAddress.getAddress().isMCGlobal());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getAddress().isMCLinkLocal(): " + interfaceAddress.getAddress().isMCLinkLocal());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getAddress().isMCNodeLocal(): " + interfaceAddress.getAddress().isMCNodeLocal());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getAddress().isMCSiteLocal(): " + interfaceAddress.getAddress().isMCSiteLocal());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getAddress().isMCOrgLocal(): " + interfaceAddress.getAddress().isMCOrgLocal());

                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getBroadcast(): " + interfaceAddress.getBroadcast());
                if(Objects.nonNull(interfaceAddress.getBroadcast()))
                {
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getBroadcast().toString(): " + interfaceAddress.getBroadcast().toString());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getBroadcast().getHostName(): " + interfaceAddress.getBroadcast().getHostName());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getBroadcast().getCanonicalHostName(): " + interfaceAddress.getBroadcast().getCanonicalHostName());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getBroadcast().getHostAddress(): " + interfaceAddress.getBroadcast().getHostAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getBroadcast().getAddress(): " + Arrays.toString(interfaceAddress.getBroadcast().getAddress()));
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getBroadcast().hashCode(): " + interfaceAddress.getBroadcast().hashCode());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getBroadcast().isAnyLocalAddress(): " + interfaceAddress.getBroadcast().isAnyLocalAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getBroadcast().isLinkLocalAddress(): " + interfaceAddress.getBroadcast().isLinkLocalAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getBroadcast().isLoopbackAddress(): " + interfaceAddress.getBroadcast().isLoopbackAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getBroadcast().isMulticastAddress(): " + interfaceAddress.getBroadcast().isMulticastAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getBroadcast().isSiteLocalAddress(): " + interfaceAddress.getBroadcast().isSiteLocalAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getBroadcast().isMCGlobal(): " + interfaceAddress.getBroadcast().isMCGlobal());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getBroadcast().isMCLinkLocal(): " + interfaceAddress.getBroadcast().isMCLinkLocal());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getBroadcast().isMCNodeLocal(): " + interfaceAddress.getBroadcast().isMCNodeLocal());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getBroadcast().isMCSiteLocal(): " + interfaceAddress.getBroadcast().isMCSiteLocal());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getInterfaceAddresses()["+j+"].getBroadcast().isMCOrgLocal(): " + interfaceAddress.getBroadcast().isMCOrgLocal());
                }
                j++;
                System.out.println("<=======End=======>");
            }


            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces(): " + networkIF.queryNetworkInterface().subInterfaces());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces().toString(): " + networkIF.queryNetworkInterface().subInterfaces().toString());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces().count(): " + networkIF.queryNetworkInterface().subInterfaces().count());


            j=0;
            networkIF.queryNetworkInterface().subInterfaces().forEach(value->
            {
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].getName(): " + value.getName());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].getDisplayName(): " + value.getDisplayName());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].toString(): " + value.toString());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].hashCode(): " + value.hashCode());

                try {
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].supportsMulticast(): " + value.supportsMulticast());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].isPointToPoint(): " + value.isPointToPoint());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].isUp(): " + value.isUp());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].isLoopback(): " + value.isLoopback());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].getHardwareAddress(): " + Arrays.toString(value.getHardwareAddress()));
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].getMTU(): " + value.getMTU());
                } catch (SocketException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].isVirtual(): " + value.isVirtual());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].getIndex(): " + value.getIndex());


                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].inetAddresses(): " + value.inetAddresses());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].inetAddresses().toString(): " + value.inetAddresses().toString());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].inetAddresses().count(): " + value.inetAddresses().count());

                value.inetAddresses().forEach((inetAddress)-> {
                    System.out.println("<=======Start=======>");
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].inetAddresses().getHostAddress(): " + inetAddress.getHostAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].inetAddresses().toString(): " + inetAddress.toString());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].inetAddresses().getHostName(): " + inetAddress.getHostName());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].inetAddresses().getCanonicalHostName(): " + inetAddress.getCanonicalHostName());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].inetAddresses().hashCode(): " + inetAddress.hashCode());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].inetAddresses().getAddress(): " + Arrays.toString(inetAddress.getAddress()));
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].inetAddresses().isLoopbackAddress(): " + inetAddress.isLoopbackAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].inetAddresses().isLinkLocalAddress(): " + inetAddress.isLinkLocalAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].inetAddresses().isMulticastAddress(): " + inetAddress.isMulticastAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].inetAddresses().isMCOrgLocal(): " + inetAddress.isMCOrgLocal());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].inetAddresses().isMCSiteLocal(): " + inetAddress.isMCSiteLocal());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].inetAddresses().isMCNodeLocal(): " + inetAddress.isMCNodeLocal());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].inetAddresses().isMCGlobal(): " + inetAddress.isMCGlobal());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].inetAddresses().isMCLinkLocal(): " + inetAddress.isMCLinkLocal());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].inetAddresses().isAnyLocalAddress(): " + inetAddress.isAnyLocalAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].inetAddresses().isSiteLocalAddress(): " + inetAddress.isSiteLocalAddress());
                    System.out.println("<=======End=======>");
                });

                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].getInetAddresses(): " + value.getInetAddresses());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].getParent(): " + value.getParent());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].getInterfaceAddresses(): " + value.getInterfaceAddresses());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].getSubInterfaces(): " + value.getSubInterfaces());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().subInterfaces()["+j+"].subInterfaces(): " + value.subInterfaces());

                j++;
            });


            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces(): " + networkIF.queryNetworkInterface().getSubInterfaces());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().toString(): " + networkIF.queryNetworkInterface().getSubInterfaces().toString());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().hasMoreElements(): " + networkIF.queryNetworkInterface().getSubInterfaces().hasMoreElements());

            java.util.Enumeration<NetworkInterface> networkInterfaceEnumeration = networkIF.queryNetworkInterface().getSubInterfaces();

            while(networkInterfaceEnumeration.hasMoreElements())
            {
                System.out.println("<=======Start=======>");
                NetworkInterface networkInterface = networkInterfaceEnumeration.nextElement();
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement(): " + networkInterface);
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().getName(): " + networkInterface.getName());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().getDisplayName(): " + networkInterface.getDisplayName());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().toString(): " + networkInterface.toString());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().getIndex(): " + networkInterface.getIndex());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().isVirtual(): " + networkInterface.isVirtual());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().hashCode(): " + networkInterface.hashCode());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().isLoopback(): " + networkInterface.isLoopback());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().isUp(): " + networkInterface.isUp());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().isPointToPoint(): " + networkInterface.isPointToPoint());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().supportsMulticast(): " + networkInterface.supportsMulticast());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().getHardwareAddress(): " + Arrays.toString(networkInterface.getHardwareAddress()));
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().getMTU(): " + networkInterface.getMTU());

                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().inetAddresses(): " + networkInterface.inetAddresses());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().inetAddresses().toString(): " + networkInterface.inetAddresses().toString());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().inetAddresses().count(): " + networkInterface.inetAddresses().count());

                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().getInetAddresses(): " + networkInterface.getInetAddresses());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().getInetAddresses().toString(): " + networkInterface.getInetAddresses().toString());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().getInetAddresses().hasMoreElements(): " + networkInterface.getInetAddresses().hasMoreElements());


                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().subInterfaces(): " + networkInterface.subInterfaces());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().subInterfaces().toString(): " + networkInterface.subInterfaces().toString());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().subInterfaces().count(): " + networkInterface.subInterfaces().count());

                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().getSubInterfaces(): " + networkInterface.getSubInterfaces());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().getSubInterfaces().toString(): " + networkInterface.getSubInterfaces().toString());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().getSubInterfaces().hasMoreElements: " + networkInterface.getSubInterfaces().hasMoreElements());

                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().getInterfaceAddresses(): " + networkInterface.getInterfaceAddresses());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().getInterfaceAddresses().toString(): " + networkInterface.getInterfaceAddresses().toString());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().getInterfaceAddresses().size(): " + networkInterface.getInterfaceAddresses().size());


                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().getParent(): " + networkInterface.getParent());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().getParent().getName(): " + networkInterface.getParent().getName());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getSubInterfaces().nextElement().getParent().toString(): " + networkInterface.getParent().toString());

                System.out.println("<=======End=======>");
            }



            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().inetAddresses(): " + networkIF.queryNetworkInterface().inetAddresses());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().inetAddresses().toString(): " + networkIF.queryNetworkInterface().inetAddresses().toString());
            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().inetAddresses().count(): " + networkIF.queryNetworkInterface().inetAddresses().count());

            j=0;
            networkIF.queryNetworkInterface().inetAddresses().forEach(inetAddress->
            {
                System.out.println("<=======Start=======>");
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().inetAddresses()["+j+"]: " + inetAddress);
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().inetAddresses()["+j+"].toString(): " + inetAddress.toString());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().inetAddresses()["+j+"].getHostName(): " + inetAddress.getHostName());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().inetAddresses()["+j+"].getCanonicalHostName(): " + inetAddress.getCanonicalHostName());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().inetAddresses()["+j+"].getHostAddress(): " + inetAddress.getHostAddress());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().inetAddresses()["+j+"].hashCode(): " + inetAddress.hashCode());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().inetAddresses()["+j+"].getAddress(): " + Arrays.toString(inetAddress.getAddress()));
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().inetAddresses()["+j+"].isLoopbackAddress(): " + inetAddress.isLoopbackAddress());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().inetAddresses()["+j+"].isMulticastAddress(): " + inetAddress.isMulticastAddress());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().inetAddresses()["+j+"].isAnyLocalAddress(): " + inetAddress.isAnyLocalAddress());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().inetAddresses()["+j+"].isLinkLocalAddress(): " + inetAddress.isLinkLocalAddress());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().inetAddresses()["+j+"].isSiteLocalAddress(): " + inetAddress.isSiteLocalAddress());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().inetAddresses()["+j+"].isMCGlobal(): " + inetAddress.isMCGlobal());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().inetAddresses()["+j+"].isMCNodeLocal(): " + inetAddress.isMCNodeLocal());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().inetAddresses()["+j+"].isMCSiteLocal(): " + inetAddress.isMCSiteLocal());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().inetAddresses()["+j+"].isMCOrgLocal(): " + inetAddress.isMCOrgLocal());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().inetAddresses()["+j+"].isMCLinkLocal(): " + inetAddress.isMCLinkLocal());
                j++;
                System.out.println("<=======End=======>");
            });



            System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent(): " + networkIF.queryNetworkInterface().getParent());
            if(Objects.nonNull(networkIF.queryNetworkInterface().getParent()))
            {
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getName(): " + networkIF.queryNetworkInterface().getParent().getName());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().toString(): " + networkIF.queryNetworkInterface().getParent().toString());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getDisplayName(): " + networkIF.queryNetworkInterface().getParent().getDisplayName());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getHardwareAddress(): " + Arrays.toString(networkIF.queryNetworkInterface().getParent().getHardwareAddress()));
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getIndex(): " + networkIF.queryNetworkInterface().getParent().getIndex());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getMTU(): " + networkIF.queryNetworkInterface().getParent().getMTU());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().isLoopback(): " + networkIF.queryNetworkInterface().getParent().isLoopback());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().isUp(): " + networkIF.queryNetworkInterface().getParent().isUp());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().isPointToPoint(): " + networkIF.queryNetworkInterface().getParent().isPointToPoint());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().isVirtual(): " + networkIF.queryNetworkInterface().getParent().isVirtual());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().supportsMulticast(): " + networkIF.queryNetworkInterface().getParent().supportsMulticast());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().hashCode(): " + networkIF.queryNetworkInterface().getParent().hashCode());

                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInetAddresses(): " + networkIF.queryNetworkInterface().getParent().getInetAddresses());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInetAddresses().toString(): " + networkIF.queryNetworkInterface().getParent().getInetAddresses().toString());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInetAddresses().hasMoreElements(): " + networkIF.queryNetworkInterface().getParent().getInetAddresses().hasMoreElements());
                java.util.Enumeration<java.net.InetAddress> inetAddressParentEnum = networkIF.queryNetworkInterface().getParent().getInetAddresses();

                while(inetAddressParentEnum.hasMoreElements())
                {
                    System.out.println("<=======Start=======>");
                    java.net.InetAddress inetAddress = inetAddressParentEnum.nextElement();
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInetAddresses().nextElement(): " + inetAddress);
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInetAddresses().nextElement().getHostName(): " + inetAddress.getHostName());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInetAddresses().nextElement().getHostAddress(): " + inetAddress.getHostAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInetAddresses().nextElement().toString(): " + inetAddress.toString());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInetAddresses().nextElement().isMulticastAddress(): " + inetAddress.isMulticastAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInetAddresses().nextElement().isSiteLocalAddress(): " + inetAddress.isSiteLocalAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInetAddresses().nextElement().hashCode(): " + inetAddress.hashCode());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInetAddresses().nextElement().getCanonicalHostName(): " + inetAddress.getCanonicalHostName());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInetAddresses().nextElement().getAddress(): " + Arrays.toString(inetAddress.getAddress()));
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInetAddresses().nextElement().isAnyLocalAddress(): " + inetAddress.isAnyLocalAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInetAddresses().nextElement().isLoopbackAddress(): " + inetAddress.isLoopbackAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInetAddresses().nextElement().isLinkLocalAddress(): " + inetAddress.isLinkLocalAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInetAddresses().nextElement().isMCLinkLocal(): " + inetAddress.isMCLinkLocal());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInetAddresses().nextElement().isMCGlobal(): " + inetAddress.isMCGlobal());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInetAddresses().nextElement().isMCOrgLocal(): " + inetAddress.isMCOrgLocal());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInetAddresses().nextElement().isMCSiteLocal(): " + inetAddress.isMCSiteLocal());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInetAddresses().nextElement().isMCNodeLocal(): " + inetAddress.isMCNodeLocal());
                    System.out.println("<=======End=======>");
                }


                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses(): " + networkIF.queryNetworkInterface().getParent().getInterfaceAddresses());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses().toString(): " + networkIF.queryNetworkInterface().getParent().getInterfaceAddresses().toString());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses().size(): " + networkIF.queryNetworkInterface().getParent().getInterfaceAddresses().size());

                j=0;
                for(InterfaceAddress interfaceAddress: networkIF.queryNetworkInterface().getParent().getInterfaceAddresses())
                {
                    System.out.println("<=======Start=======>");
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].toString(): " + interfaceAddress.toString());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getNetworkPrefixLength(): " + interfaceAddress.getNetworkPrefixLength());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].hashCode(): " + interfaceAddress.hashCode());

                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getAddress(): " + interfaceAddress.getAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getAddress().toString(): " + interfaceAddress.getAddress().toString());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getAddress().getHostName(): " + interfaceAddress.getAddress().getHostName());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getAddress().getCanonicalHostName(): " + interfaceAddress.getAddress().getCanonicalHostName());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getAddress().getHostAddress(): " + interfaceAddress.getAddress().getHostAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getAddress().getAddress(): " + Arrays.toString(interfaceAddress.getAddress().getAddress()));
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getAddress().hashCode(): " + interfaceAddress.getAddress().hashCode());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getAddress().isAnyLocalAddress(): " + interfaceAddress.getAddress().isAnyLocalAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getAddress().isLinkLocalAddress(): " + interfaceAddress.getAddress().isLinkLocalAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getAddress().isLoopbackAddress(): " + interfaceAddress.getAddress().isLoopbackAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getAddress().isMulticastAddress(): " + interfaceAddress.getAddress().isMulticastAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getAddress().isSiteLocalAddress(): " + interfaceAddress.getAddress().isSiteLocalAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getAddress().isMCGlobal(): " + interfaceAddress.getAddress().isMCGlobal());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getAddress().isMCLinkLocal(): " + interfaceAddress.getAddress().isMCLinkLocal());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getAddress().isMCNodeLocal(): " + interfaceAddress.getAddress().isMCNodeLocal());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getAddress().isMCSiteLocal(): " + interfaceAddress.getAddress().isMCSiteLocal());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getAddress().isMCOrgLocal(): " + interfaceAddress.getAddress().isMCOrgLocal());

                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getBroadcast(): " + interfaceAddress.getBroadcast());
                    if(Objects.nonNull(interfaceAddress.getBroadcast()))
                    {
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getBroadcast().toString(): " + interfaceAddress.getBroadcast().toString());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getBroadcast().getHostName(): " + interfaceAddress.getBroadcast().getHostName());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getBroadcast().getCanonicalHostName(): " + interfaceAddress.getBroadcast().getCanonicalHostName());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getBroadcast().getHostAddress(): " + interfaceAddress.getBroadcast().getHostAddress());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getBroadcast().getAddress(): " + Arrays.toString(interfaceAddress.getBroadcast().getAddress()));
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getBroadcast().hashCode(): " + interfaceAddress.getBroadcast().hashCode());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getBroadcast().isAnyLocalAddress(): " + interfaceAddress.getBroadcast().isAnyLocalAddress());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getBroadcast().isLinkLocalAddress(): " + interfaceAddress.getBroadcast().isLinkLocalAddress());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getBroadcast().isLoopbackAddress(): " + interfaceAddress.getBroadcast().isLoopbackAddress());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getBroadcast().isMulticastAddress(): " + interfaceAddress.getBroadcast().isMulticastAddress());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getBroadcast().isSiteLocalAddress(): " + interfaceAddress.getBroadcast().isSiteLocalAddress());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getBroadcast().isMCGlobal(): " + interfaceAddress.getBroadcast().isMCGlobal());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getBroadcast().isMCLinkLocal(): " + interfaceAddress.getBroadcast().isMCLinkLocal());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getBroadcast().isMCNodeLocal(): " + interfaceAddress.getBroadcast().isMCNodeLocal());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getBroadcast().isMCSiteLocal(): " + interfaceAddress.getBroadcast().isMCSiteLocal());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getInterfaceAddresses()["+j+"].getBroadcast().isMCOrgLocal(): " + interfaceAddress.getBroadcast().isMCOrgLocal());
                    }
                    j++;
                    System.out.println("<=======End=======>");
                }


                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces(): " + networkIF.queryNetworkInterface().getParent().subInterfaces());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces().toString(): " + networkIF.queryNetworkInterface().getParent().subInterfaces().toString());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces().count(): " + networkIF.queryNetworkInterface().getParent().subInterfaces().count());


                j=0;
                networkIF.queryNetworkInterface().getParent().subInterfaces().forEach(value->
                {
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].getName(): " + value.getName());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].getDisplayName(): " + value.getDisplayName());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].toString(): " + value.toString());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].hashCode(): " + value.hashCode());

                    try {
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].supportsMulticast(): " + value.supportsMulticast());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].isPointToPoint(): " + value.isPointToPoint());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].isUp(): " + value.isUp());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].isLoopback(): " + value.isLoopback());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].getHardwareAddress(): " + Arrays.toString(value.getHardwareAddress()));
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].getMTU(): " + value.getMTU());
                    } catch (SocketException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].isVirtual(): " + value.isVirtual());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].getIndex(): " + value.getIndex());


                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].inetAddresses(): " + value.inetAddresses());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].inetAddresses().toString(): " + value.inetAddresses().toString());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].inetAddresses().count(): " + value.inetAddresses().count());

                    value.inetAddresses().forEach((inetAddress)-> {
                        System.out.println("<=======Start=======>");
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].inetAddresses().getHostAddress(): " + inetAddress.getHostAddress());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].inetAddresses().toString(): " + inetAddress.toString());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].inetAddresses().getHostName(): " + inetAddress.getHostName());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].inetAddresses().getCanonicalHostName(): " + inetAddress.getCanonicalHostName());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].inetAddresses().hashCode(): " + inetAddress.hashCode());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].inetAddresses().getAddress(): " + Arrays.toString(inetAddress.getAddress()));
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].inetAddresses().isLoopbackAddress(): " + inetAddress.isLoopbackAddress());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].inetAddresses().isLinkLocalAddress(): " + inetAddress.isLinkLocalAddress());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].inetAddresses().isMulticastAddress(): " + inetAddress.isMulticastAddress());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].inetAddresses().isMCOrgLocal(): " + inetAddress.isMCOrgLocal());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].inetAddresses().isMCSiteLocal(): " + inetAddress.isMCSiteLocal());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].inetAddresses().isMCNodeLocal(): " + inetAddress.isMCNodeLocal());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].inetAddresses().isMCGlobal(): " + inetAddress.isMCGlobal());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].inetAddresses().isMCLinkLocal(): " + inetAddress.isMCLinkLocal());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].inetAddresses().isAnyLocalAddress(): " + inetAddress.isAnyLocalAddress());
                        System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].inetAddresses().isSiteLocalAddress(): " + inetAddress.isSiteLocalAddress());
                        System.out.println("<=======End=======>");
                    });

                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].getInetAddresses(): " + value.getInetAddresses());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].getParent(): " + value.getParent());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].getInterfaceAddresses(): " + value.getInterfaceAddresses());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].getSubInterfaces(): " + value.getSubInterfaces());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().subInterfaces()["+j+"].subInterfaces(): " + value.subInterfaces());

                    j++;
                });


                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces(): " + networkIF.queryNetworkInterface().getParent().getSubInterfaces());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().toString(): " + networkIF.queryNetworkInterface().getParent().getSubInterfaces().toString());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().hasMoreElements(): " + networkIF.queryNetworkInterface().getParent().getSubInterfaces().hasMoreElements());

                java.util.Enumeration<NetworkInterface> networkInterfaceParentEnum = networkIF.queryNetworkInterface().getParent().getSubInterfaces();

                while(networkInterfaceParentEnum.hasMoreElements())
                {
                    System.out.println("<=======Start=======>");
                    NetworkInterface networkInterface = networkInterfaceParentEnum.nextElement();
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement(): " + networkInterface);
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().getName(): " + networkInterface.getName());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().getDisplayName(): " + networkInterface.getDisplayName());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().toString(): " + networkInterface.toString());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().getIndex(): " + networkInterface.getIndex());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().isVirtual(): " + networkInterface.isVirtual());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().hashCode(): " + networkInterface.hashCode());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().isLoopback(): " + networkInterface.isLoopback());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().isUp(): " + networkInterface.isUp());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().isPointToPoint(): " + networkInterface.isPointToPoint());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().supportsMulticast(): " + networkInterface.supportsMulticast());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().getHardwareAddress(): " + Arrays.toString(networkInterface.getHardwareAddress()));
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().getMTU(): " + networkInterface.getMTU());

                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().inetAddresses(): " + networkInterface.inetAddresses());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().inetAddresses().toString(): " + networkInterface.inetAddresses().toString());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().inetAddresses().count(): " + networkInterface.inetAddresses().count());

                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().getInetAddresses(): " + networkInterface.getInetAddresses());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().getInetAddresses().toString(): " + networkInterface.getInetAddresses().toString());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().getInetAddresses().hasMoreElements(): " + networkInterface.getInetAddresses().hasMoreElements());


                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().subInterfaces(): " + networkInterface.subInterfaces());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().subInterfaces().toString(): " + networkInterface.subInterfaces().toString());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().subInterfaces().count(): " + networkInterface.subInterfaces().count());

                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().getSubInterfaces(): " + networkInterface.getSubInterfaces());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().getSubInterfaces().toString(): " + networkInterface.getSubInterfaces().toString());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().getSubInterfaces().hasMoreElements: " + networkInterface.getSubInterfaces().hasMoreElements());

                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().getInterfaceAddresses(): " + networkInterface.getInterfaceAddresses());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().getInterfaceAddresses().toString(): " + networkInterface.getInterfaceAddresses().toString());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().getInterfaceAddresses().size(): " + networkInterface.getInterfaceAddresses().size());


                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().getParent(): " + networkInterface.getParent());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().getParent().getName(): " + networkInterface.getParent().getName());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().getSubInterfaces().nextElement().getParent().toString(): " + networkInterface.getParent().toString());

                    System.out.println("<=======End=======>");
                }



                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().inetAddresses(): " + networkIF.queryNetworkInterface().getParent().inetAddresses());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().inetAddresses().toString(): " + networkIF.queryNetworkInterface().getParent().inetAddresses().toString());
                System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().inetAddresses().count(): " + networkIF.queryNetworkInterface().getParent().inetAddresses().count());

                j=0;
                networkIF.queryNetworkInterface().getParent().inetAddresses().forEach(inetAddress->
                {
                    System.out.println("<=======Start=======>");
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().inetAddresses()["+j+"]: " + inetAddress);
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().inetAddresses()["+j+"].toString(): " + inetAddress.toString());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().inetAddresses()["+j+"].getHostName(): " + inetAddress.getHostName());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().inetAddresses()["+j+"].getCanonicalHostName(): " + inetAddress.getCanonicalHostName());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().inetAddresses()["+j+"].getHostAddress(): " + inetAddress.getHostAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().inetAddresses()["+j+"].hashCode(): " + inetAddress.hashCode());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().inetAddresses()["+j+"].getAddress(): " + Arrays.toString(inetAddress.getAddress()));
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().inetAddresses()["+j+"].isLoopbackAddress(): " + inetAddress.isLoopbackAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().inetAddresses()["+j+"].isMulticastAddress(): " + inetAddress.isMulticastAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().inetAddresses()["+j+"].isAnyLocalAddress(): " + inetAddress.isAnyLocalAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().inetAddresses()["+j+"].isLinkLocalAddress(): " + inetAddress.isLinkLocalAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().inetAddresses()["+j+"].isSiteLocalAddress(): " + inetAddress.isSiteLocalAddress());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().inetAddresses()["+j+"].isMCGlobal(): " + inetAddress.isMCGlobal());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().inetAddresses()["+j+"].isMCNodeLocal(): " + inetAddress.isMCNodeLocal());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().inetAddresses()["+j+"].isMCSiteLocal(): " + inetAddress.isMCSiteLocal());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().inetAddresses()["+j+"].isMCOrgLocal(): " + inetAddress.isMCOrgLocal());
                    System.out.println("systemInfo.getHardware().getNetworkIFs()["+i+"].queryNetworkInterface().getParent().inetAddresses()["+j+"].isMCLinkLocal(): " + inetAddress.isMCLinkLocal());
                    j++;
                    System.out.println("<=======End=======>");
                });
                i++;
                System.out.println("<=======End=======>");
                }
        }














        System.out.println();System.out.println();System.out.println();

        System.out.println("systemInfo.getHardware().getPowerSources(): " + systemInfo.getHardware().getPowerSources());
        System.out.println("systemInfo.getHardware().getPowerSources().toString(): " + systemInfo.getHardware().getPowerSources().toString());
        System.out.println("systemInfo.getHardware().getPowerSources().size(): " + systemInfo.getHardware().getPowerSources().size());
        i=0;
        for(PowerSource powerSource : systemInfo.getHardware().getPowerSources())
        {
            System.out.println("<=======Start=======>");
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"]: " + powerSource);
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].toString(): " + powerSource.toString());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].getName(): " + powerSource.getName());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].getDeviceName(): " + powerSource.getDeviceName());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].getSerialNumber(): " + powerSource.getSerialNumber());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].getChemistry(): " + powerSource.getChemistry());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].getManufacturer(): " + powerSource.getManufacturer());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].getManufactureDate(): " + powerSource.getManufactureDate());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].getAmperage(): " + powerSource.getAmperage());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].getDesignCapacity(): " + powerSource.getDesignCapacity());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].getCurrentCapacity(): " + powerSource.getCurrentCapacity());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].getCycleCount(): " + powerSource.getCycleCount());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].getMaxCapacity(): " + powerSource.getMaxCapacity());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].getPowerUsageRate(): " + powerSource.getPowerUsageRate());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].getVoltage(): " + powerSource.getVoltage());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].isCharging(): " + powerSource.isCharging());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].isDischarging(): " + powerSource.isDischarging());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].isPowerOnLine(): " + powerSource.isPowerOnLine());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].updateAttributes(): " + powerSource.updateAttributes());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].getTemperature(): " + powerSource.getTemperature());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].getTimeRemainingEstimated(): " + powerSource.getTimeRemainingEstimated());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].getTimeRemainingInstant(): " + powerSource.getTimeRemainingInstant());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].getRemainingCapacityPercent(): " + powerSource.getRemainingCapacityPercent());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].getCapacityUnits(): " + powerSource.getCapacityUnits());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].getCapacityUnits().toString(): " + powerSource.getCapacityUnits().toString());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].getCapacityUnits().name(): " + powerSource.getCapacityUnits().name());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].getCapacityUnits().ordinal(): " + powerSource.getCapacityUnits().ordinal());
            System.out.println("systemInfo.getHardware().getPowerSources()["+i+"].getCapacityUnits().hashCode(): " + powerSource.getCapacityUnits().hashCode());
            i++;
            System.out.println("<=======End=======>");
        }

        System.out.println();System.out.println();System.out.println();

        System.out.println("systemInfo.getHardware().getSensors(): " + systemInfo.getHardware().getSensors());
        System.out.println("systemInfo.getHardware().getSensors().toString(): " + systemInfo.getHardware().getSensors().toString());
        System.out.println("systemInfo.getHardware().getSensors().hashCode(): " + systemInfo.getHardware().getSensors().hashCode());
        System.out.println("systemInfo.getHardware().getSensors().getCpuTemperature(): " + systemInfo.getHardware().getSensors().getCpuTemperature());
        System.out.println("systemInfo.getHardware().getSensors().getCpuVoltage(): " + systemInfo.getHardware().getSensors().getCpuVoltage());
        System.out.println("systemInfo.getHardware().getSensors().getFanSpeeds(): " + Arrays.toString(systemInfo.getHardware().getSensors().getFanSpeeds()));

        System.out.println();System.out.println();System.out.println();

        System.out.println("systemInfo.getHardware().getSoundCards(): " + systemInfo.getHardware().getSoundCards());
        System.out.println("systemInfo.getHardware().getSoundCards().toString(): " + systemInfo.getHardware().getSoundCards().toString());
        System.out.println("systemInfo.getHardware().getSoundCards().size(): " + systemInfo.getHardware().getSoundCards().size());

        i=0;
        for(SoundCard soundCard : systemInfo.getHardware().getSoundCards())
        {
            System.out.println("<=======Start=======>");
            System.out.println("systemInfo.getHardware().getSoundCards()["+i+"]: " + soundCard);
            System.out.println("systemInfo.getHardware().getSoundCards()["+i+"].getName(): " + soundCard.getName());
            System.out.println("systemInfo.getHardware().getSoundCards()["+i+"].toString(): " + soundCard.toString());
            System.out.println("systemInfo.getHardware().getSoundCards()["+i+"].hashCode(): " + soundCard.hashCode());
            System.out.println("systemInfo.getHardware().getSoundCards()["+i+"].getCodec(): " + soundCard.getCodec());
            System.out.println("systemInfo.getHardware().getSoundCards()["+i+"].getDriverVersion(): " + soundCard.getDriverVersion());
            i++;
            System.out.println("<=======End=======>");
        }



        System.out.println();System.out.println();System.out.println();
        System.out.println();System.out.println();System.out.println();
        System.out.println();System.out.println();System.out.println();




        System.out.println("systemInfo.getOperatingSystem(): " + systemInfo.getOperatingSystem());

        System.out.println("systemInfo.getOperatingSystem().toString(): " + systemInfo.getOperatingSystem().toString());
        System.out.println("systemInfo.getOperatingSystem().getFamily(): " + systemInfo.getOperatingSystem().getFamily());
        System.out.println("systemInfo.getOperatingSystem().hashCode(): " + systemInfo.getOperatingSystem().hashCode());
        System.out.println("systemInfo.getOperatingSystem().getManufacturer(): " + systemInfo.getOperatingSystem().getManufacturer());
        System.out.println("systemInfo.getOperatingSystem().getBitness(): " + systemInfo.getOperatingSystem().getBitness());
        System.out.println("systemInfo.getOperatingSystem().getProcessId(): " + systemInfo.getOperatingSystem().getProcessId());
        System.out.println("systemInfo.getOperatingSystem().getProcessCount(): " + systemInfo.getOperatingSystem().getProcessCount());
        System.out.println("systemInfo.getOperatingSystem().getSystemBootTime(): " + systemInfo.getOperatingSystem().getSystemBootTime());
        System.out.println("systemInfo.getOperatingSystem().getSystemUptime(): " + systemInfo.getOperatingSystem().getSystemUptime());
        System.out.println("systemInfo.getOperatingSystem().getThreadId(): " + systemInfo.getOperatingSystem().getThreadId());
        System.out.println("systemInfo.getOperatingSystem().getThreadCount(): " + systemInfo.getOperatingSystem().getThreadCount());
        System.out.println("systemInfo.getOperatingSystem().isElevated(): " + systemInfo.getOperatingSystem().isElevated());


        System.out.println("systemInfo.getOperatingSystem().getVersionInfo(): " + systemInfo.getOperatingSystem().getVersionInfo());
        System.out.println("systemInfo.getOperatingSystem().getVersionInfo().toString(): " + systemInfo.getOperatingSystem().getVersionInfo().toString());
        System.out.println("systemInfo.getOperatingSystem().getVersionInfo().getVersion(): " + systemInfo.getOperatingSystem().getVersionInfo().getVersion());
        System.out.println("systemInfo.getOperatingSystem().getVersionInfo().getBuildNumber(): " + systemInfo.getOperatingSystem().getVersionInfo().getBuildNumber());
        System.out.println("systemInfo.getOperatingSystem().getVersionInfo().getCodeName(): " + systemInfo.getOperatingSystem().getVersionInfo().getCodeName());
        System.out.println("systemInfo.getOperatingSystem().getVersionInfo().hashCode(): " + systemInfo.getOperatingSystem().getVersionInfo().hashCode());


        System.out.println();System.out.println();System.out.println();


        System.out.println("systemInfo.getOperatingSystem().getProcesses(): " + systemInfo.getOperatingSystem().getProcesses());
        System.out.println("systemInfo.getOperatingSystem().getProcesses().toString(): " + systemInfo.getOperatingSystem().getProcesses().toString());
        System.out.println("systemInfo.getOperatingSystem().getProcesses().size(): " + systemInfo.getOperatingSystem().getProcesses().size());

        i=0;
        for(OSProcess oSProcess: systemInfo.getOperatingSystem().getProcesses())
        {
            System.out.println("<=======Start=======>");
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"]: " + oSProcess);
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].toString(): " + oSProcess.toString());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getName(): " + oSProcess.getName());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getContextSwitches(): " + oSProcess.getContextSwitches());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getUser(): " + oSProcess.getUser());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getUserID(): " + oSProcess.getUserID());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getUserTime(): " + oSProcess.getUserTime());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getPriority(): " + oSProcess.getPriority());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getProcessID(): " + oSProcess.getProcessID());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getParentProcessID(): " + oSProcess.getParentProcessID());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getPath(): " + oSProcess.getPath());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getCommandLine(): " + oSProcess.getCommandLine());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getCurrentWorkingDirectory(): " + oSProcess.getCurrentWorkingDirectory());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getGroup(): " + oSProcess.getGroup());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getGroupID(): " + oSProcess.getGroupID());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getAffinityMask(): " + oSProcess.getAffinityMask());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getBitness(): " + oSProcess.getBitness());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getBytesRead(): " + oSProcess.getBytesRead());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getBytesWritten(): " + oSProcess.getBytesWritten());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getHardOpenFileLimit(): " + oSProcess.getHardOpenFileLimit());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getSoftOpenFileLimit(): " + oSProcess.getSoftOpenFileLimit());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getKernelTime(): " + oSProcess.getKernelTime());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getMinorFaults(): " + oSProcess.getMinorFaults());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getMajorFaults(): " + oSProcess.getMajorFaults());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getOpenFiles(): " + oSProcess.getOpenFiles());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getProcessCpuLoadCumulative(): " + oSProcess.getProcessCpuLoadCumulative());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getResidentSetSize(): " + oSProcess.getResidentSetSize());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getStartTime(): " + oSProcess.getStartTime());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadCount(): " + oSProcess.getThreadCount());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getUpTime(): " + oSProcess.getUpTime());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getVirtualSize(): " + oSProcess.getVirtualSize());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].updateAttributes(): " + oSProcess.updateAttributes());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getProcessCpuLoadBetweenTicks(oSProcess): " + oSProcess.getProcessCpuLoadBetweenTicks(oSProcess));

            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails(): " + oSProcess.getThreadDetails());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails().toString(): " + oSProcess.getThreadDetails().toString());
            System.out.println();
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails().size(): " + oSProcess.getThreadDetails().size());
            System.out.println();

            j=0;
            for(OSThread oSThread: oSProcess.getThreadDetails())
            {
                System.out.println("<=======Start=======>");
                System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails()["+j+"]: " + oSThread);
                System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails()["+j+"].getName(): " + oSThread.getName());
                System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails()["+j+"].toString(): " + oSThread.toString());
                System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails()["+j+"].updateAttributes(): " + oSThread.updateAttributes());
                System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails()["+j+"].getStartTime(): " + oSThread.getStartTime());
                System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails()["+j+"].getThreadId(): " + oSThread.getThreadId());
                System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails()["+j+"].getContextSwitches(): " + oSThread.getContextSwitches());
                System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails()["+j+"].getUpTime(): " + oSThread.getUpTime());
                System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails()["+j+"].getUserTime(): " + oSThread.getUserTime());
                System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails()["+j+"].getKernelTime(): " + oSThread.getKernelTime());
                System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails()["+j+"].getMinorFaults(): " + oSThread.getMinorFaults());
                System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails()["+j+"].getMajorFaults(): " + oSThread.getMajorFaults());
                System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails()["+j+"].getOwningProcessId(): " + oSThread.getOwningProcessId());
                System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails()["+j+"].getPriority(): " + oSThread.getPriority());
                System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails()["+j+"].getStartMemoryAddress(): " + oSThread.getStartMemoryAddress());
                System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails()["+j+"].getThreadCpuLoadCumulative(): " + oSThread.getThreadCpuLoadCumulative());
                System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails()["+j+"].getThreadCpuLoadBetweenTicks(oSThread): " + oSThread.getThreadCpuLoadBetweenTicks(oSThread));

                System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails()["+j+"].getState(): " + oSThread.getState());
                System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails()["+j+"].getState().name(): " + oSThread.getState().name());
                System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails()["+j+"].getState().toString(): " + oSThread.getState().toString());
                System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getThreadDetails()["+j+"].getState().ordinal(): " + oSThread.getState().ordinal());

                j++;
                System.out.println("<=======End=======>");
            }

            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getState(): " + oSProcess.getState());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getState().name(): " + oSProcess.getState().name());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getState().toString(): " + oSProcess.getState().toString());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getState().ordinal(): " + oSProcess.getState().ordinal());



            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getArguments(): " + oSProcess.getArguments());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getArguments().toString(): " + oSProcess.getArguments().toString());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getArguments().size(): " + oSProcess.getArguments().size());

            j=0;
            for(String arguments: oSProcess.getArguments())
            {
                System.out.println("<=======Start=======>");
                System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getArguments()["+j+"]: " + arguments);
                System.out.println("<=======End=======>");
                j++;
            }


            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getEnvironmentVariables(): " + oSProcess.getEnvironmentVariables());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getEnvironmentVariables().toString(): " + oSProcess.getEnvironmentVariables().toString());
            System.out.println("systemInfo.getOperatingSystem().getProcesses()["+i+"].getEnvironmentVariables().size(): " + oSProcess.getEnvironmentVariables().size());
            oSProcess.getEnvironmentVariables().forEach((key,value)-> System.out.println(key+" : "+value));
            i++;
            System.out.println("<=======End=======>");
        }

        System.out.println();System.out.println();System.out.println();

        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess(): " + systemInfo.getOperatingSystem().getCurrentProcess());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().toString(): " + systemInfo.getOperatingSystem().getCurrentProcess().toString());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getName(): " + systemInfo.getOperatingSystem().getCurrentProcess().getName());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getContextSwitches(): " + systemInfo.getOperatingSystem().getCurrentProcess().getContextSwitches());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getUser(): " + systemInfo.getOperatingSystem().getCurrentProcess().getUser());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getUserID(): " + systemInfo.getOperatingSystem().getCurrentProcess().getUserID());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getUserTime(): " + systemInfo.getOperatingSystem().getCurrentProcess().getUserTime());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getPriority(): " + systemInfo.getOperatingSystem().getCurrentProcess().getPriority());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getProcessID(): " + systemInfo.getOperatingSystem().getCurrentProcess().getProcessID());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getParentProcessID(): " + systemInfo.getOperatingSystem().getCurrentProcess().getParentProcessID());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getPath(): " + systemInfo.getOperatingSystem().getCurrentProcess().getPath());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getCommandLine(): " + systemInfo.getOperatingSystem().getCurrentProcess().getCommandLine());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getCurrentWorkingDirectory(): " + systemInfo.getOperatingSystem().getCurrentProcess().getCurrentWorkingDirectory());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getGroup(): " + systemInfo.getOperatingSystem().getCurrentProcess().getGroup());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getGroupID(): " + systemInfo.getOperatingSystem().getCurrentProcess().getGroupID());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getAffinityMask(): " + systemInfo.getOperatingSystem().getCurrentProcess().getAffinityMask());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getBitness(): " + systemInfo.getOperatingSystem().getCurrentProcess().getBitness());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getBytesRead(): " + systemInfo.getOperatingSystem().getCurrentProcess().getBytesRead());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getBytesWritten(): " + systemInfo.getOperatingSystem().getCurrentProcess().getBytesWritten());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getHardOpenFileLimit(): " + systemInfo.getOperatingSystem().getCurrentProcess().getHardOpenFileLimit());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getSoftOpenFileLimit(): " + systemInfo.getOperatingSystem().getCurrentProcess().getSoftOpenFileLimit());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getKernelTime(): " + systemInfo.getOperatingSystem().getCurrentProcess().getKernelTime());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getMinorFaults(): " + systemInfo.getOperatingSystem().getCurrentProcess().getMinorFaults());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getMajorFaults(): " + systemInfo.getOperatingSystem().getCurrentProcess().getMajorFaults());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getOpenFiles(): " + systemInfo.getOperatingSystem().getCurrentProcess().getOpenFiles());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getProcessCpuLoadCumulative(): " + systemInfo.getOperatingSystem().getCurrentProcess().getProcessCpuLoadCumulative());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getResidentSetSize(): " + systemInfo.getOperatingSystem().getCurrentProcess().getResidentSetSize());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getStartTime(): " + systemInfo.getOperatingSystem().getCurrentProcess().getStartTime());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadCount(): " + systemInfo.getOperatingSystem().getCurrentProcess().getThreadCount());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getUpTime(): " + systemInfo.getOperatingSystem().getCurrentProcess().getUpTime());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getVirtualSize(): " + systemInfo.getOperatingSystem().getCurrentProcess().getVirtualSize());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().updateAttributes(): " + systemInfo.getOperatingSystem().getCurrentProcess().updateAttributes());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getProcessCpuLoadBetweenTicks(systemInfo.getOperatingSystem().getCurrentProcess()): " + systemInfo.getOperatingSystem().getCurrentProcess().getProcessCpuLoadBetweenTicks(systemInfo.getOperatingSystem().getCurrentProcess()));


        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails(): " + systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails().toString(): " + systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails().toString());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails().size(): " + systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails().size());

        i=0;
        for(OSThread oSThread: systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails())
        {
            System.out.println("<=======Start=======>");
            System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails()["+i+"]: " + oSThread);
            System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails()["+i+"].getName(): " + oSThread.getName());
            System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails()["+i+"].toString(): " + oSThread.toString());
            System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails()["+i+"].updateAttributes(): " + oSThread.updateAttributes());
            System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails()["+i+"].getStartTime(): " + oSThread.getStartTime());
            System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails()["+i+"].getThreadId(): " + oSThread.getThreadId());
            System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails()["+i+"].getContextSwitches(): " + oSThread.getContextSwitches());
            System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails()["+i+"].getUpTime(): " + oSThread.getUpTime());
            System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails()["+i+"].getUserTime(): " + oSThread.getUserTime());
            System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails()["+i+"].getKernelTime(): " + oSThread.getKernelTime());
            System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails()["+i+"].getMinorFaults(): " + oSThread.getMinorFaults());
            System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails()["+i+"].getMajorFaults(): " + oSThread.getMajorFaults());
            System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails()["+i+"].getOwningProcessId(): " + oSThread.getOwningProcessId());
            System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails()["+i+"].getPriority(): " + oSThread.getPriority());
            System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails()["+i+"].getStartMemoryAddress(): " + oSThread.getStartMemoryAddress());
            System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails()["+i+"].getThreadCpuLoadCumulative(): " + oSThread.getThreadCpuLoadCumulative());
            System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails()["+i+"].getThreadCpuLoadBetweenTicks(oSThread): " + oSThread.getThreadCpuLoadBetweenTicks(oSThread));


            System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails()["+i+"].getState(): " + oSThread.getState());
            System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails()["+i+"].getState().name(): " + oSThread.getState().name());
            System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails()["+i+"].getState().toString(): " + oSThread.getState().toString());
            System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getThreadDetails()["+i+"].getState().ordinal(): " + oSThread.getState().ordinal());

            i++;
            System.out.println("<=======End=======>");
        }

        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getState(): " + systemInfo.getOperatingSystem().getCurrentProcess().getState());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getState().name(): " + systemInfo.getOperatingSystem().getCurrentProcess().getState().name());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getState().toString(): " + systemInfo.getOperatingSystem().getCurrentProcess().getState().toString());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getState().ordinal(): " + systemInfo.getOperatingSystem().getCurrentProcess().getState().ordinal());



        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getArguments(): " + systemInfo.getOperatingSystem().getCurrentProcess().getArguments());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getArguments().toString(): " + systemInfo.getOperatingSystem().getCurrentProcess().getArguments().toString());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getArguments().size(): " + systemInfo.getOperatingSystem().getCurrentProcess().getArguments().size());

        i=0;
        for(String arguments: systemInfo.getOperatingSystem().getCurrentProcess().getArguments())
        {
            System.out.println("<=======Start=======>");
            System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getArguments()["+i+"]: " + arguments);
            i++;
            System.out.println("<=======End=======>");
        }


        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getEnvironmentVariables(): " + systemInfo.getOperatingSystem().getCurrentProcess().getEnvironmentVariables());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getEnvironmentVariables().toString(): " + systemInfo.getOperatingSystem().getCurrentProcess().getEnvironmentVariables().toString());
        System.out.println("systemInfo.getOperatingSystem().getCurrentProcess().getEnvironmentVariables().size(): " + systemInfo.getOperatingSystem().getCurrentProcess().getEnvironmentVariables().size());
        systemInfo.getOperatingSystem().getCurrentProcess().getEnvironmentVariables().forEach((key,value)-> System.out.println(key+" : "+value));


        System.out.println();System.out.println();System.out.println();


        System.out.println("systemInfo.getOperatingSystem().getCurrentThread(): " + systemInfo.getOperatingSystem().getCurrentThread());
        System.out.println("systemInfo.getOperatingSystem().getCurrentThread().getName(): " + systemInfo.getOperatingSystem().getCurrentThread().getName());
        System.out.println("systemInfo.getOperatingSystem().getCurrentThread().toString(): " + systemInfo.getOperatingSystem().getCurrentThread().toString());
        System.out.println("systemInfo.getOperatingSystem().getCurrentThread().updateAttributes(): " + systemInfo.getOperatingSystem().getCurrentThread().updateAttributes());
        System.out.println("systemInfo.getOperatingSystem().getCurrentThread().getStartTime(): " + systemInfo.getOperatingSystem().getCurrentThread().getStartTime());
        System.out.println("systemInfo.getOperatingSystem().getCurrentThread().getThreadId(): " + systemInfo.getOperatingSystem().getCurrentThread().getThreadId());
        System.out.println("systemInfo.getOperatingSystem().getCurrentThread().getContextSwitches(): " + systemInfo.getOperatingSystem().getCurrentThread().getContextSwitches());
        System.out.println("systemInfo.getOperatingSystem().getCurrentThread().getUpTime(): " + systemInfo.getOperatingSystem().getCurrentThread().getUpTime());
        System.out.println("systemInfo.getOperatingSystem().getCurrentThread().getUserTime(): " + systemInfo.getOperatingSystem().getCurrentThread().getUserTime());
        System.out.println("systemInfo.getOperatingSystem().getCurrentThread().getKernelTime(): " + systemInfo.getOperatingSystem().getCurrentThread().getKernelTime());
        System.out.println("systemInfo.getOperatingSystem().getCurrentThread().getMinorFaults(): " + systemInfo.getOperatingSystem().getCurrentThread().getMinorFaults());
        System.out.println("systemInfo.getOperatingSystem().getCurrentThread().getMajorFaults(): " + systemInfo.getOperatingSystem().getCurrentThread().getMajorFaults());
        System.out.println("systemInfo.getOperatingSystem().getCurrentThread().getOwningProcessId(): " + systemInfo.getOperatingSystem().getCurrentThread().getOwningProcessId());
        System.out.println("systemInfo.getOperatingSystem().getCurrentThread().getPriority(): " + systemInfo.getOperatingSystem().getCurrentThread().getPriority());
        System.out.println("systemInfo.getOperatingSystem().getCurrentThread().getStartMemoryAddress(): " + systemInfo.getOperatingSystem().getCurrentThread().getStartMemoryAddress());
        System.out.println("systemInfo.getOperatingSystem().getCurrentThread().getThreadCpuLoadCumulative(): " + systemInfo.getOperatingSystem().getCurrentThread().getThreadCpuLoadCumulative());
        System.out.println("systemInfo.getOperatingSystem().getCurrentThread().getThreadCpuLoadBetweenTicks(systemInfo.getOperatingSystem().getCurrentThread()): " + systemInfo.getOperatingSystem().getCurrentThread().getThreadCpuLoadBetweenTicks(systemInfo.getOperatingSystem().getCurrentThread()));


        System.out.println("systemInfo.getOperatingSystem().getCurrentThread().getState(): " + systemInfo.getOperatingSystem().getCurrentThread().getState());
        System.out.println("systemInfo.getOperatingSystem().getCurrentThread().getState().name(): " + systemInfo.getOperatingSystem().getCurrentThread().getState().name());
        System.out.println("systemInfo.getOperatingSystem().getCurrentThread().getState().toString(): " + systemInfo.getOperatingSystem().getCurrentThread().getState().toString());
        System.out.println("systemInfo.getOperatingSystem().getCurrentThread().getState().ordinal(): " + systemInfo.getOperatingSystem().getCurrentThread().getState().ordinal());

        System.out.println();System.out.println();System.out.println();

        System.out.println("systemInfo.getOperatingSystem().getFileSystem(): " + systemInfo.getOperatingSystem().getFileSystem());
        System.out.println("systemInfo.getOperatingSystem().getFileSystem().toString(): " + systemInfo.getOperatingSystem().getFileSystem().toString());
        System.out.println("systemInfo.getOperatingSystem().getFileSystem().hashCode(): " + systemInfo.getOperatingSystem().getFileSystem().hashCode());
        System.out.println("systemInfo.getOperatingSystem().getFileSystem().getMaxFileDescriptors(): " + systemInfo.getOperatingSystem().getFileSystem().getMaxFileDescriptors());
        System.out.println("systemInfo.getOperatingSystem().getFileSystem().getOpenFileDescriptors(): " + systemInfo.getOperatingSystem().getFileSystem().getOpenFileDescriptors());
        System.out.println("systemInfo.getOperatingSystem().getFileSystem().getMaxFileDescriptorsPerProcess(): " + systemInfo.getOperatingSystem().getFileSystem().getMaxFileDescriptorsPerProcess());

        System.out.println("systemInfo.getOperatingSystem().getFileSystem().getFileStores(): " + systemInfo.getOperatingSystem().getFileSystem().getFileStores());
        System.out.println("systemInfo.getOperatingSystem().getFileSystem().getFileStores().toString(): " + systemInfo.getOperatingSystem().getFileSystem().getFileStores().toString());
        System.out.println("systemInfo.getOperatingSystem().getFileSystem().getFileStores().size(): " + systemInfo.getOperatingSystem().getFileSystem().getFileStores().size());
        System.out.println("systemInfo.getOperatingSystem().getFileSystem().getFileStores().hashCode(): " + systemInfo.getOperatingSystem().getFileSystem().getFileStores().hashCode());

        i=0;
        for(OSFileStore oSFileStore : systemInfo.getOperatingSystem().getFileSystem().getFileStores())
        {
            System.out.println("<=======Start=======>");
            System.out.println("systemInfo.getOperatingSystem().getFileSystem().getFileStores()["+i+"]: " + oSFileStore);
            System.out.println("systemInfo.getOperatingSystem().getFileSystem().getFileStores()["+i+"].getName(): " + oSFileStore.getName());
            System.out.println("systemInfo.getOperatingSystem().getFileSystem().getFileStores()["+i+"].toString(): " + oSFileStore.toString());
            System.out.println("systemInfo.getOperatingSystem().getFileSystem().getFileStores()["+i+"].getDescription(): " + oSFileStore.getDescription());
            System.out.println("systemInfo.getOperatingSystem().getFileSystem().getFileStores()["+i+"].getOptions(): " + oSFileStore.getOptions());
            System.out.println("systemInfo.getOperatingSystem().getFileSystem().getFileStores()["+i+"].getType(): " + oSFileStore.getType());
            System.out.println("systemInfo.getOperatingSystem().getFileSystem().getFileStores()["+i+"].getTotalSpace(): " + oSFileStore.getTotalSpace());
            System.out.println("systemInfo.getOperatingSystem().getFileSystem().getFileStores()["+i+"].getFreeSpace(): " + oSFileStore.getFreeSpace());
            System.out.println("systemInfo.getOperatingSystem().getFileSystem().getFileStores()["+i+"].getLabel(): " + oSFileStore.getLabel());
            System.out.println("systemInfo.getOperatingSystem().getFileSystem().getFileStores()["+i+"].getLogicalVolume(): " + oSFileStore.getLogicalVolume());
            System.out.println("systemInfo.getOperatingSystem().getFileSystem().getFileStores()["+i+"].getMount(): " + oSFileStore.getMount());
            System.out.println("systemInfo.getOperatingSystem().getFileSystem().getFileStores()["+i+"].getUUID(): " + oSFileStore.getUUID());
            System.out.println("systemInfo.getOperatingSystem().getFileSystem().getFileStores()["+i+"].getVolume(): " + oSFileStore.getVolume());
            System.out.println("systemInfo.getOperatingSystem().getFileSystem().getFileStores()["+i+"].getUsableSpace(): " + oSFileStore.getUsableSpace());
            System.out.println("systemInfo.getOperatingSystem().getFileSystem().getFileStores()["+i+"].getTotalInodes(): " + oSFileStore.getTotalInodes());
            System.out.println("systemInfo.getOperatingSystem().getFileSystem().getFileStores()["+i+"].getFreeInodes(): " + oSFileStore.getFreeInodes());
            System.out.println("systemInfo.getOperatingSystem().getFileSystem().getFileStores()["+i+"].updateAttributes(): " + oSFileStore.updateAttributes());
            i++;
            System.out.println("<=======End=======>");
        }


        System.out.println();System.out.println();System.out.println();

        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats(): " + systemInfo.getOperatingSystem().getInternetProtocolStats());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().toString(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().toString());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().hashCode(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().hashCode());

        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().toString(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().toString());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().hashCode(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().hashCode());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().getOutResets(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().getOutResets());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().getConnectionsEstablished(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().getConnectionsEstablished());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().getConnectionsActive(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().getConnectionsActive());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().getConnectionsPassive(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().getConnectionsPassive());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().getConnectionFailures(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().getConnectionFailures());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().getConnectionsReset(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().getConnectionsReset());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().getInErrors(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().getInErrors());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().getSegmentsReceived(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().getSegmentsReceived());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().getSegmentsSent(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().getSegmentsSent());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().getSegmentsRetransmitted(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats().getSegmentsRetransmitted());


        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().toString(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().toString());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().hashCode(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().hashCode());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().getOutResets(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().getOutResets());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().getConnectionsEstablished(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().getConnectionsEstablished());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().getConnectionsActive(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().getConnectionsActive());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().getConnectionsPassive(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().getConnectionsPassive());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().getConnectionFailures(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().getConnectionFailures());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().getConnectionsReset(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().getConnectionsReset());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().getInErrors(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().getInErrors());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().getSegmentsReceived(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().getSegmentsReceived());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().getSegmentsSent(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().getSegmentsSent());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().getSegmentsRetransmitted(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getTCPv6Stats().getSegmentsRetransmitted());



        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv4Stats(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv4Stats());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv4Stats().toString(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv4Stats().toString());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv4Stats().hashCode(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv4Stats().hashCode());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv4Stats().getDatagramsNoPort(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv4Stats().getDatagramsNoPort());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv4Stats().getDatagramsReceived(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv4Stats().getDatagramsReceived());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv4Stats().getDatagramsSent(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv4Stats().getDatagramsSent());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv4Stats().getDatagramsReceivedErrors(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv4Stats().getDatagramsReceivedErrors());


        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv6Stats(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv6Stats());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv6Stats().toString(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv6Stats().toString());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv6Stats().hashCode(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv6Stats().hashCode());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv6Stats().getDatagramsNoPort(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv6Stats().getDatagramsNoPort());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv6Stats().getDatagramsReceived(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv6Stats().getDatagramsReceived());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv6Stats().getDatagramsSent(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv6Stats().getDatagramsSent());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv6Stats().getDatagramsReceivedErrors(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getUDPv6Stats().getDatagramsReceivedErrors());

        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections().toString(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections().toString());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections().size(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections().size());
        System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections().hashCode(): " + systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections().hashCode());

        i=0;
        for(InternetProtocolStats.IPConnection iPConnection : systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections())
        {
            System.out.println("<=======Start=======>");
            System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections()["+i+"]: " + iPConnection);
            System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections()["+i+"].toString(): " + iPConnection.toString());
            System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections()["+i+"].getType(): " + iPConnection.getType());
            System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections()["+i+"].getForeignAddress(): " + Arrays.toString(iPConnection.getForeignAddress()));
            System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections()["+i+"].getForeignPort(): " + iPConnection.getForeignPort());
            System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections()["+i+"].getLocalAddress(): " + Arrays.toString(iPConnection.getLocalAddress()));
            System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections()["+i+"].getLocalPort(): " + iPConnection.getLocalPort());
            System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections()["+i+"].getowningProcessId(): " + iPConnection.getowningProcessId());
            System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections()["+i+"].getReceiveQueue(): " + iPConnection.getReceiveQueue());
            System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections()["+i+"].getTransmitQueue(): " + iPConnection.getTransmitQueue());

            System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections()["+i+"].getState(): " + iPConnection.getState());
            System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections()["+i+"].getState().name(): " + iPConnection.getState().name());
            System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections()["+i+"].getState().toString(): " + iPConnection.getState().toString());
            System.out.println("systemInfo.getOperatingSystem().getInternetProtocolStats().getConnections()["+i+"].getState().ordinal(): " + iPConnection.getState().ordinal());
            i++;
            System.out.println("<=======End=======>");
        }


        System.out.println();System.out.println();System.out.println();

        System.out.println("systemInfo.getOperatingSystem().getNetworkParams(): " + systemInfo.getOperatingSystem().getNetworkParams());
        System.out.println("systemInfo.getOperatingSystem().getNetworkParams().toString(): " + systemInfo.getOperatingSystem().getNetworkParams().toString());
        System.out.println("systemInfo.getOperatingSystem().getNetworkParams().getDomainName(): " + systemInfo.getOperatingSystem().getNetworkParams().getDomainName());
        System.out.println("systemInfo.getOperatingSystem().getNetworkParams().getHostName(): " + systemInfo.getOperatingSystem().getNetworkParams().getHostName());
        System.out.println("systemInfo.getOperatingSystem().getNetworkParams().hashCode(): " + systemInfo.getOperatingSystem().getNetworkParams().hashCode());
        System.out.println("systemInfo.getOperatingSystem().getNetworkParams().getIpv4DefaultGateway(): " + systemInfo.getOperatingSystem().getNetworkParams().getIpv4DefaultGateway());
        System.out.println("systemInfo.getOperatingSystem().getNetworkParams().getIpv6DefaultGateway(): " + systemInfo.getOperatingSystem().getNetworkParams().getIpv6DefaultGateway());
        System.out.println("systemInfo.getOperatingSystem().getNetworkParams().getDnsServers(): " + Arrays.toString(systemInfo.getOperatingSystem().getNetworkParams().getDnsServers()));


        System.out.println();System.out.println();System.out.println();

        System.out.println("systemInfo.getOperatingSystem().getServices(): " + systemInfo.getOperatingSystem().getServices());
        System.out.println("systemInfo.getOperatingSystem().getServices().toString(): " + systemInfo.getOperatingSystem().getServices().toString());
        System.out.println("systemInfo.getOperatingSystem().getServices().size(): " + systemInfo.getOperatingSystem().getServices().size());

        i=0;
        for(oshi.software.os.OSService oSService : systemInfo.getOperatingSystem().getServices())
        {
            System.out.println("<=======Start=======>");
            System.out.println("systemInfo.getOperatingSystem().getServices()["+i+"]: " + oSService);
            System.out.println("systemInfo.getOperatingSystem().getServices()["+i+"].getName(): " + oSService.getName());
            System.out.println("systemInfo.getOperatingSystem().getServices()["+i+"].toString(): " + oSService.toString());
            System.out.println("systemInfo.getOperatingSystem().getServices()["+i+"].hashCode(): " + oSService.hashCode());
            System.out.println("systemInfo.getOperatingSystem().getServices()["+i+"].getProcessID(): " + oSService.getProcessID());

            System.out.println("systemInfo.getOperatingSystem().getServices()["+i+"].getState(): " + oSService.getState());
            System.out.println("systemInfo.getOperatingSystem().getServices()["+i+"].getState().name(): " + oSService.getState().name());
            System.out.println("systemInfo.getOperatingSystem().getServices()["+i+"].getState().toString(): " + oSService.getState().toString());
            System.out.println("systemInfo.getOperatingSystem().getServices()["+i+"].getState().hashCode(): " + oSService.getState().hashCode());
            System.out.println("systemInfo.getOperatingSystem().getServices()["+i+"].getState().ordinal(): " + oSService.getState().ordinal());
            i++;
            System.out.println("<=======End=======>");
        }

        System.out.println();System.out.println();System.out.println();

        System.out.println("systemInfo.getOperatingSystem().getSessions(): " + systemInfo.getOperatingSystem().getSessions());
        System.out.println("systemInfo.getOperatingSystem().getSessions().toString(): " + systemInfo.getOperatingSystem().getSessions().toString());
        System.out.println("systemInfo.getOperatingSystem().getSessions().size(): " + systemInfo.getOperatingSystem().getSessions().size());

        i=0;
        for(oshi.software.os.OSSession oSSession: systemInfo.getOperatingSystem().getSessions())
        {
            System.out.println("<=======Start=======>");
            System.out.println("systemInfo.getOperatingSystem().getSessions()["+i+"]: " + oSSession);
            System.out.println("systemInfo.getOperatingSystem().getSessions()["+i+"].toString(): " + oSSession.toString());
            System.out.println("systemInfo.getOperatingSystem().getSessions()["+i+"].getHost(): " + oSSession.getHost());
            System.out.println("systemInfo.getOperatingSystem().getSessions()["+i+"].getTerminalDevice(): " + oSSession.getTerminalDevice());
            System.out.println("systemInfo.getOperatingSystem().getSessions()["+i+"].getUserName(): " + oSSession.getUserName());
            System.out.println("systemInfo.getOperatingSystem().getSessions()["+i+"].getLoginTime(): " + oSSession.getLoginTime());
            i++;
            System.out.println("<=======End=======>");
        }


        System.out.println();System.out.println();System.out.println();

    }

    private static void copySigarBin()
    {
        // Source directory path within src/main/resources
        String sourceDirectoryPath = "sigar-bin";

        // Destination directory path within target
        String destinationDirectoryPath = "target/sigar-bin";

        // Get the path of the source directory
        Path sourcePath = Paths.get(".", sourceDirectoryPath);

        // Get the path of the destination directory
        Path destinationPath = Paths.get(destinationDirectoryPath);

        try {
            // Create the target directory if it doesn't exist
            if (!Files.exists(destinationPath)) {
                Files.createDirectories(destinationPath);
            }

            // Copy the entire source directory to the destination
            Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path targetDir = destinationPath.resolve(sourcePath.relativize(dir));
                    Files.createDirectories(targetDir);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.copy(file, destinationPath.resolve(sourcePath.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                    return FileVisitResult.CONTINUE;
                }
            });

            System.out.println("Directory copied successfully from " + sourcePath + " to " + destinationPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
