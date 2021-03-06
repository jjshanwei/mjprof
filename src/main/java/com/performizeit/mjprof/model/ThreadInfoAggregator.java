package com.performizeit.mjprof.model;

import com.performizeit.mjprof.parser.ThreadInfo;

import java.util.*;

import static com.performizeit.mjprof.parser.ThreadInfoProps.*;


public class ThreadInfoAggregator {
    private final List<String> props;
    HashSet<String> propsMap = new HashSet<String>();

    HashMap<List<Object>, ThreadInfo> aggregator = new HashMap<List<Object>, ThreadInfo>();

    public ThreadInfoAggregator(List<String> props) {
        this.props = props;
        for (String prop : props) propsMap.add(prop);
    }

    public void accumulateThreadInfo(ThreadInfo threadInfo) {
        ArrayList<Object> key = generateKey(threadInfo);
        ThreadInfo target = aggregator.get(key);
        if (target == null) {
            aggregator.put(key, threadInfo);
        } else {
            mergeProfiles(target, threadInfo);
            mergeCounts(target, threadInfo);
            mergeNonKeyProps(target, threadInfo);
        }
    }
    public ArrayList<ThreadInfo> getAggrInfos() {
        return new ArrayList<ThreadInfo>(aggregator.values()) ;

    }

    private void mergeCounts(ThreadInfo target, ThreadInfo threadInfo) {
        Integer countTarget = (Integer) target.getVal(COUNT);
        Integer count = (Integer) threadInfo.getVal(COUNT);
        if (count == null) count = 1;
        if (countTarget == null) countTarget = 1;
        countTarget += count;
        target.setVal(COUNT, countTarget);
    }

    private void mergeProfiles(ThreadInfo target, ThreadInfo threadInfo) {
        Profile targetProfile = (Profile) target.getVal(STACK);
        Profile prof =   (Profile) threadInfo.getVal(STACK);
        if (prof == null) return;
        if (targetProfile == null) target.setVal(STACK,prof);
        targetProfile.addMulti(prof);
    }


    public void mergeNonKeyProps(ThreadInfo target, ThreadInfo threadInfo) {
        ArrayList<String> keys = new ArrayList<String>(threadInfo.getProps());
        for (String prop1 : keys) {
            if (prop1.equals(STACK) || prop1.equals(COUNT) || propsMap.contains(prop1))
                continue; //    already merged to specially
            if (propsMap.contains(prop1)) continue; //     key properties are not merged

            Object valTarget = target.getVal(prop1);
            Object val = threadInfo.getVal(prop1);
            if (valTarget == null) {
                target.setVal(prop1, val);
            } else if (!val.equals(valTarget)) {   // we have more than one
                if (prop1.equals(DAEMON)) {
                    target.setVal(prop1, false);
                } if (prop1.equals(LOS)) {
                    target.setVal(prop1, "\t- *");
                } else {
                    target.setVal(prop1, "*");
                }
            }

        }


    }

    ArrayList<Object> generateKey(ThreadInfo threadInfo) {
        ArrayList<Object> key = new ArrayList<Object>();
        for (String prop : props) {
            key.add(threadInfo.getVal(prop));

        }
        return key;

    }


}
