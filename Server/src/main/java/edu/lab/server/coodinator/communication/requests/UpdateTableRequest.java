package edu.lab.server.coodinator.communication.requests;

import edu.lab.server.coodinator.communication.Request;
import edu.lab.server.coodinator.communication.RequestCode;

public class UpdateTableRequest extends Request {

    public UpdateTableRequest(RequestCode code) {
        super(code);
    }

    @Override
    protected void buildInfoBytes() {
        //доп инфы нет
    }


}
