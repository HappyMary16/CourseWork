package ua.com.courseWork.model;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class Entity implements Serializable {

    private static final long serialVersionUID = 1068756419944483685L;

    protected Long id;
}
