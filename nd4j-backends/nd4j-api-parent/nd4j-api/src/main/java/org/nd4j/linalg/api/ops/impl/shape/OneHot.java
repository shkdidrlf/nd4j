package org.nd4j.linalg.api.ops.impl.shape;

import lombok.val;
import org.nd4j.autodiff.samediff.SDVariable;
import org.nd4j.autodiff.samediff.SameDiff;
import org.nd4j.imports.descriptors.properties.PropertyMapping;
import org.nd4j.imports.graphmapper.tf.TFGraphMapper;
import org.nd4j.linalg.api.ops.DynamicCustomOp;
import org.tensorflow.framework.AttrValue;
import org.tensorflow.framework.GraphDef;
import org.tensorflow.framework.NodeDef;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by susaneraly on 3/14/18.
 */
public class OneHot extends DynamicCustomOp {

    private int depth;
    private int axis = -1;
    private float on;
    private float off;

    public  OneHot() {

    }

    public OneHot(SameDiff sameDiff, SDVariable indices, int depth) {
        super(null, sameDiff,  new SDVariable[] {indices}, false);
        this.depth = depth;
        this.axis = -1;
        this.on = 1.00f;
        this.off = 0.00f;
        addArgs();
    }

    public OneHot(SameDiff sameDiff, SDVariable indices, int depth, int axis, float on, float off) {
        super(null, sameDiff,  new SDVariable[] {indices}, false);
        this.depth = depth;
        this.axis = axis;
        this.on = on;
        this.off = off;
        addArgs();
    }


    protected void addArgs() {
        addIArgument(depth);
        addIArgument(axis);
        addTArgument(on);
        addTArgument(off);
    }

    @Override
    public void initFromTensorFlow(NodeDef nodeDef, SameDiff initWith, Map<String, AttrValue> attributesForNode, GraphDef graph) {
        TFGraphMapper.getInstance().initFunctionFromProperties(nodeDef.getOp(), this, attributesForNode, nodeDef, graph);
        addArgs();
    }


    @Override
    public Map<String, Map<String, PropertyMapping>> mappingsForFunction() {
        Map<String, Map<String, PropertyMapping>> ret = new HashMap<>();
        Map<String,PropertyMapping> attrs = new LinkedHashMap<>();

        val on = PropertyMapping.builder()
                .propertyNames(new String[]{"on"})
                .tfInputPosition(2)
                .build();
        attrs.put("on", on);

        val off = PropertyMapping.builder()
                .propertyNames(new String[]{"off"})
                .tfInputPosition(3)
                .build();
        attrs.put("off", off);

        ret.put(tensorflowName(),attrs);
        return ret;
    }

    @Override
    public String tensorflowName() {
        return "OneHot";
    }

    @Override
    public String opName() {
        return "onehot";
    }
}
