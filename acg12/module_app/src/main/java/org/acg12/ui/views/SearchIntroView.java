package org.acg12.ui.views;

import android.view.View;
import android.widget.TextView;

import com.acg12.lib.ui.base.ViewImpl;

import org.acg12.R;
import org.acg12.entity.Subject;
import org.acg12.entity.SubjectCrt;
import org.acg12.entity.SubjectOffprint;
import org.acg12.entity.SubjectSong;
import org.acg12.entity.SubjectStaff;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/31.
 */

public class SearchIntroView extends ViewImpl {

    @BindView(R.id.tv_subject_info_other_title)
    TextView tv_subject_info_other_title;
    @BindView(R.id.tv_subject_info_other)
    TextView tv_subject_info_other;
    @BindView(R.id.tv_subject_info_into_title)
    TextView tv_subject_info_into_title;
    @BindView(R.id.tv_subject_info_into)
    TextView tv_subject_info_into;
    @BindView(R.id.tv_subject_info_crt_title)
    TextView tv_subject_info_crt_title;
    @BindView(R.id.tv_subject_info_crt)
    TextView tv_subject_info_crt;
    @BindView(R.id.tv_subject_info_staff_title)
    TextView tv_subject_info_staff_title;
    @BindView(R.id.tv_subject_info_staff)
    TextView tv_subject_info_staff;
    @BindView(R.id.tv_subject_info_song_title)
    TextView tv_subject_info_song_title;
    @BindView(R.id.tv_subject_info_song)
    TextView tv_subject_info_song;
    @BindView(R.id.tv_subject_info_offprint_title)
    TextView tv_subject_info_offprint_title;
    @BindView(R.id.tv_subject_info_offprint)
    TextView tv_subject_info_offprint;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_intro;
    }

    @Override
    public void created() {
        super.created();
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
    }

    public void bindData(Subject subject) {
        if (subject == null) {
            return;
        }
        String info = "";
        if (info.isEmpty()) {
            tv_subject_info_other_title.setVisibility(View.GONE);
            tv_subject_info_other.setVisibility(View.GONE);
        } else {
            tv_subject_info_other_title.setVisibility(View.VISIBLE);
            tv_subject_info_other.setVisibility(View.VISIBLE);
            tv_subject_info_other.setText(info);
        }

        String summary = subject.getSummary();
        if (summary == null || summary.isEmpty()) {
            tv_subject_info_into_title.setVisibility(View.GONE);
            tv_subject_info_into.setVisibility(View.GONE);
        } else {
            tv_subject_info_into_title.setVisibility(View.VISIBLE);
            tv_subject_info_into.setVisibility(View.VISIBLE);
            summary = summary.replace(" " , "\n");
            tv_subject_info_into.setText(summary);
        }

        List<SubjectCrt> subjectCrtList = subject.getCrtList();
        if (subjectCrtList == null || subjectCrtList.size() == 0) {
            tv_subject_info_crt_title.setVisibility(View.GONE);
            tv_subject_info_crt.setVisibility(View.GONE);
        } else {
            String crt = "";
            int i = 0;
            for (SubjectCrt subjectCrt : subjectCrtList) {
                if (i == 0) {
                    crt += subjectCrt.getName() + "：" + subjectCrt.getpName();
                } else {
                    crt += "\n" + subjectCrt.getName() + "：" + subjectCrt.getpName();
                }
                i++;
            }
            tv_subject_info_crt_title.setVisibility(View.VISIBLE);
            tv_subject_info_crt.setVisibility(View.VISIBLE);
            tv_subject_info_crt.setText(crt);
        }

        List<SubjectStaff> subjectStaffList = subject.getStaffList();
        if (subjectStaffList == null || subjectStaffList.size() == 0) {
            tv_subject_info_staff_title.setVisibility(View.GONE);
            tv_subject_info_staff.setVisibility(View.GONE);
        } else {
            String staff = "";
            StringBuffer sb = new StringBuffer();
            int i = 0;
            for (SubjectStaff subjectStaff : subjectStaffList) {
                if (i == 0) {
//                    staff += ;
                    sb.append(subjectStaff.getJob() + "：" + subjectStaff.getName());
                } else {
                    SubjectStaff last = subjectStaffList.get(i - 1);
                    if (subjectStaff.getJob().equals(last.getJob())) {
                        String empty = "";
                        for (int j = 0; j < subjectStaff.getJob().length()+1; j++) {
                            empty += "\u3000";
                        }
                        sb.append("\n" + empty + subjectStaff.getName());
                    } else {
                        sb.append("\n" + subjectStaff.getJob() + "：" + subjectStaff.getName());
                    }
                }
                i++;
            }
            tv_subject_info_staff_title.setVisibility(View.VISIBLE);
            tv_subject_info_staff.setVisibility(View.VISIBLE);
            tv_subject_info_staff.setText(sb.toString());
        }

        List<SubjectSong> subjectSongList = subject.getSongList();
        if (subjectSongList == null || subjectSongList.size() == 0) {
            tv_subject_info_song_title.setVisibility(View.GONE);
            tv_subject_info_song.setVisibility(View.GONE);
        } else {
            String song = "";
            int i = 0;
            for (SubjectSong subjectSong : subjectSongList) {
                if (i == 0) {
                    song += subjectSong.getTitle();
                } else {
                    song += "\n" + subjectSong.getTitle();
                }
                i++;
            }
            tv_subject_info_song_title.setVisibility(View.VISIBLE);
            tv_subject_info_song.setVisibility(View.VISIBLE);
            tv_subject_info_song.setText(song);
        }

        List<SubjectOffprint> subjectOffprintList = subject.getOffprintList();
        if (subjectOffprintList == null || subjectOffprintList.size() == 0) {
            tv_subject_info_offprint_title.setVisibility(View.GONE);
            tv_subject_info_offprint.setVisibility(View.GONE);
        } else {
            String offprint = "";
            int i = 0;
            for (SubjectOffprint subjectOffprint : subjectOffprintList) {
                if (i == 0) {
                    offprint += subjectOffprint.getName();
                } else {
                    offprint += "\n" + subjectOffprint.getName();
                }
                i++;
            }
            tv_subject_info_offprint_title.setVisibility(View.VISIBLE);
            tv_subject_info_offprint.setVisibility(View.VISIBLE);
            tv_subject_info_offprint.setText(offprint);
        }
    }
}
