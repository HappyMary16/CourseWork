package ua.com.courseWork.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EdgeData extends Entity {

    private static final long serialVersionUID = 1361816177928277020L;

    private Long fromId;

    private Long toId;
}


