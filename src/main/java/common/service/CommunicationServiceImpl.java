package common.service;

import common.repository.CommunicationRepository;
import common.dao.CaseInfo;
import common.dao.Communication;
import common.entity.MainCommunicationEntity;
import common.entity.MessagesEntity;
import common.repository.MessageRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CommunicationServiceImpl implements ICommunicationService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    CommunicationRepository communicationRepository;


    @Autowired
    private EntityManager entityManager;


    @Override
    public Communication createCase(Communication request) {
        if (request != null) {
            if (request.getMessage() != null) {
                MainCommunicationEntity mainCommunicationEntity = MainCommunicationEntity.build(0L, request.getFromId(), request.getToId());
                mainCommunicationEntity = communicationRepository.save(mainCommunicationEntity);
                if (mainCommunicationEntity.getCaseID() != 0L) {
                    messageRepository.save(MessagesEntity.build(0L, mainCommunicationEntity.getCaseID(), request.getMessage(), request.getMessageDirection(), LocalDateTime.now()));
                    request.setCaseId(mainCommunicationEntity.getCaseID());
                    return request;
                }
            }
        }
        return null;
    }


    public List<Communication> getMessages(long caseId) {
        List<MessagesEntity> conversation = messageRepository.findBycaseIDFK(caseId);
        Optional<MainCommunicationEntity> mainCommunicationEntitie = communicationRepository.findById(caseId);
        List<Communication> response = new ArrayList<>();
        if (mainCommunicationEntitie.isPresent() && !conversation.isEmpty()) {
            MainCommunicationEntity txnCom = mainCommunicationEntitie.get();
            for (MessagesEntity msgs : conversation) {
                response.add(Communication.build(txnCom.getCommunicationFrom(), txnCom.getCommunicationTo(), msgs.getMessage(), msgs.getMessageDirection(), caseId, msgs.getMessagePK(), msgs.getCreatedDate()));
            }
            return response;
        }
        return null;
    }


    @Override
    public List<CaseInfo> getCaseId(long from) {
        Optional<List<MainCommunicationEntity>> clientConversation = communicationRepository.findBycommunicationFrom(from);
        Optional<List<MainCommunicationEntity>> ownerConversation = communicationRepository.findBycommunicationTo(from);
        List<CaseInfo> caseInfos = new ArrayList<>();
        if (clientConversation.isPresent()) {
            caseInfos =  this.getInfo(clientConversation.get());
        }
        if (ownerConversation.isPresent()) {
            caseInfos.addAll(this.getInfo(ownerConversation.get()));
        }

        Set<CaseInfo> set = new HashSet<>(caseInfos);
        return new ArrayList<>(set);
    }

    public List<CaseInfo> getInfo(List<MainCommunicationEntity> data )
    {
        List<CaseInfo> caseInfos = new ArrayList<>();
        for (MainCommunicationEntity entity : data) {
            caseInfos.add(CaseInfo.build(entity.getCaseID(), entity.getCommunicationTo(), entity.getCommunicationFrom()));
        }
        return caseInfos;
    }

    @Override
    public boolean SendMessage(long caseId, Communication request) {

        Optional<MainCommunicationEntity> optionalData = communicationRepository.findById(caseId);
        if (optionalData.isPresent()) {
            if (request.getMessage() != null) {
                messageRepository.save(MessagesEntity.build(0L, caseId, request.getMessage(), request.getMessageDirection(), LocalDateTime.now()));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isInvestigationExists(long toId, long fromId) {
       Optional<MainCommunicationEntity> optionalMainCommunicationEntity = communicationRepository.findBycommunicationToAndCommunicationFrom(toId,fromId);
       return optionalMainCommunicationEntity.isPresent();

    }
}
