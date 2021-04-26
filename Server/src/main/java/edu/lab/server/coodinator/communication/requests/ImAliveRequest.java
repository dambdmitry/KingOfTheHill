package edu.lab.server.coodinator.communication.requests;

import edu.lab.server.coodinator.communication.Request;
import edu.lab.server.coodinator.communication.RequestCode;

public class ImAliveRequest extends Request {

    public ImAliveRequest(RequestCode code) {
        super(code);
    }

    @Override
    protected void buildInfoBytes() {
        //Доп. инфы нет
    }
}
