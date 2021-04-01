package edu.lab.controller.socket.communication.requests;

import edu.lab.controller.socket.communication.Request;
import edu.lab.controller.socket.communication.RequestCode;

public class UpdateTableRequest extends Request {

    public UpdateTableRequest(RequestCode code) {
        super(code);
    }

    @Override
    protected void buildInfoBytes() {
        //доп инфы нет
    }


}
