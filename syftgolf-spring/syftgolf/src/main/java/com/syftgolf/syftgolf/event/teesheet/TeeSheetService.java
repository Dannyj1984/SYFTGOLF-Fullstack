package com.syftgolf.syftgolf.event.teesheet;

import com.syftgolf.syftgolf.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeeSheetService {


    TeeSheetRepository teeSheetRepository;

    public TeeSheetService(TeeSheetRepository teeSheetRepository) {
        this.teeSheetRepository = teeSheetRepository;
    }

    public TeeSheet updateTeeSheet(long id, TeeSheet teeSheet) {
        TeeSheet inDB = teeSheetRepository.getOne(id);
        inDB.setP1t1(teeSheet.getP1t1());
        inDB.setP1t2(teeSheet.getP1t2());
        inDB.setP1t3(teeSheet.getP1t3());
        inDB.setP1t4(teeSheet.getP1t4());
        inDB.setP2t1(teeSheet.getP2t1());
        inDB.setP2t2(teeSheet.getP2t2());
        inDB.setP2t3(teeSheet.getP2t3());
        inDB.setP2t4(teeSheet.getP2t4());
        inDB.setP3t1(teeSheet.getP3t1());
        inDB.setP3t2(teeSheet.getP3t2());
        inDB.setP3t3(teeSheet.getP3t3());
        inDB.setP3t4(teeSheet.getP3t4());
        inDB.setP4t1(teeSheet.getP4t1());
        inDB.setP4t2(teeSheet.getP4t2());
        inDB.setP4t3(teeSheet.getP4t3());
        inDB.setP4t4(teeSheet.getP4t4());
        inDB.setTeetime1(teeSheet.getTeetime1());
        inDB.setTeetime2(teeSheet.getTeetime2());
        inDB.setTeetime3(teeSheet.getTeetime3());
        inDB.setTeetime4(teeSheet.getTeetime4());

        return teeSheetRepository.save(inDB);
    }



}
