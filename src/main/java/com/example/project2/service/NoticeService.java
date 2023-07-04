package com.example.project2.service;

import com.example.project2.domain.Notice;
import com.example.project2.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    public List<Notice> noticeList() {
        List<Notice> list = noticeMapper.getNoticeList();
        return list;
    }

    public Notice getNotice(Integer id) {
        Notice notice = noticeMapper.getNotice(id);
        return notice;
    }

    public boolean addNotice(Notice notice) {
        int cnt = noticeMapper.addNotice(notice);
        return cnt == 1;
    }

    public boolean modifyNotice(Notice notice) {
        int cnt = noticeMapper.modifyNotice(notice);
        return cnt == 1;
    }

    public boolean removeNotice(Integer id) {
        int cnt = noticeMapper.removeNotice(id);
        return cnt == 1;
    }
}
