package com.cb.demo.userProfile.config;

import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.reporters.RemoteReporter;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.jaegertracing.thrift.internal.senders.UdpSender;
import io.opentracing.Tracer;
import io.opentracing.util.AutoFinishScopeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.support.IndexManager;

import java.util.Arrays;
import java.util.List;

@org.springframework.context.annotation.Configuration
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    @Autowired
    private Environment env;

    @Autowired
    private Tracer tracer;


    @Override
    public IndexManager indexManager() {
        return new IndexManager(false, true, true);
    }

    @Override
    protected List<String> getBootstrapHosts() {
        return Arrays.asList(env.getProperty("spring.couchbase.bootstrap-hosts").split(","));
    }

    @Override
    protected String getBucketName() {
        return env.getProperty("spring.couchbase.bucket.name");
    }

    @Override
    protected String getBucketPassword() {
        return env.getProperty("spring.couchbase.bucket.password");
    }

    private static final String AGENT_HOST = "127.0.0.1";

    protected CouchbaseEnvironment getEnvironment() {

        Tracer tracer = new JaegerTracer.Builder("couchbase")
                .withReporter(new RemoteReporter.Builder()
                        .withSender(new UdpSender(AGENT_HOST, 6831, 0))
                        .build())
                .withSampler(new ConstSampler(true))
                .withScopeManager(new AutoFinishScopeManager())
                .build();

        return DefaultCouchbaseEnvironment.builder()
                .operationTracingEnabled(true)
                .tracer(tracer)
                .build();
    }
}
