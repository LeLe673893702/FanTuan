package com.anonymouser.book.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by YandZD on 2017/8/30.
 */

public class CategoryBookItemBean {

    private int total;
    private boolean ok;
    private List<BooksBean> books;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<BooksBean> getBooks() {
        return books;
    }

    public void setBooks(List<BooksBean> books) {
        this.books = books;
    }

    public static class BooksBean implements Parcelable {
        /**
         * _id : 5816b415b06d1d32157790b1
         * title : 圣墟
         * author : 辰东
         * shortIntro : 在破败中崛起，在寂灭中复苏。沧海成尘，雷电枯竭，那一缕幽雾又一次临近大地，世间的枷锁被打开了，一个全新的世界就此揭开神秘的一角……
         * cover : /agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F1228859%2F_1228859_441552.jpg%2F
         * site : zhuishuvip
         * majorCate : 玄幻
         * minorCate : 东方玄幻
         * sizetype : -1
         * superscript :
         * contentType : txt
         * allowMonthly : false
         * banned : 0
         * latelyFollower : 306968
         * retentionRatio : 72.01
         * lastChapter : 第606章 女神入瓮
         * tags : ["玄幻","东方玄幻"]
         */

        private String _id;
        private String title;
        private String author;
        private String shortIntro;
        private String cover;
        private String site;
        private String majorCate;
        private String minorCate;
        private int sizetype;
        private String superscript;
        private String contentType;
        private boolean allowMonthly;
        private int banned;
        private int latelyFollower;
        private String retentionRatio;
        private String lastChapter;
        private List<String> tags;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getShortIntro() {
            return shortIntro;
        }

        public void setShortIntro(String shortIntro) {
            this.shortIntro = shortIntro;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public String getMajorCate() {
            return majorCate;
        }

        public void setMajorCate(String majorCate) {
            this.majorCate = majorCate;
        }

        public String getMinorCate() {
            return minorCate;
        }

        public void setMinorCate(String minorCate) {
            this.minorCate = minorCate;
        }

        public int getSizetype() {
            return sizetype;
        }

        public void setSizetype(int sizetype) {
            this.sizetype = sizetype;
        }

        public String getSuperscript() {
            return superscript;
        }

        public void setSuperscript(String superscript) {
            this.superscript = superscript;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public boolean isAllowMonthly() {
            return allowMonthly;
        }

        public void setAllowMonthly(boolean allowMonthly) {
            this.allowMonthly = allowMonthly;
        }

        public int getBanned() {
            return banned;
        }

        public void setBanned(int banned) {
            this.banned = banned;
        }

        public int getLatelyFollower() {
            return latelyFollower;
        }

        public void setLatelyFollower(int latelyFollower) {
            this.latelyFollower = latelyFollower;
        }

        public String getRetentionRatio() {
            return retentionRatio;
        }

        public void setRetentionRatio(String retentionRatio) {
            this.retentionRatio = retentionRatio;
        }

        public String getLastChapter() {
            return lastChapter;
        }

        public void setLastChapter(String lastChapter) {
            this.lastChapter = lastChapter;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        @Override
        public String toString() {
            return "BooksBean{" +
                    "_id='" + _id + '\'' +
                    ", title='" + title + '\'' +
                    ", author='" + author + '\'' +
                    ", shortIntro='" + shortIntro + '\'' +
                    ", cover='" + cover + '\'' +
                    ", site='" + site + '\'' +
                    ", majorCate='" + majorCate + '\'' +
                    ", minorCate='" + minorCate + '\'' +
                    ", sizetype=" + sizetype +
                    ", superscript='" + superscript + '\'' +
                    ", contentType='" + contentType + '\'' +
                    ", allowMonthly=" + allowMonthly +
                    ", banned=" + banned +
                    ", latelyFollower=" + latelyFollower +
                    ", retentionRatio='" + retentionRatio + '\'' +
                    ", lastChapter='" + lastChapter + '\'' +
                    ", tags=" + tags +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this._id);
            dest.writeString(this.title);
            dest.writeString(this.author);
            dest.writeString(this.shortIntro);
            dest.writeString(this.cover);
            dest.writeString(this.site);
            dest.writeString(this.majorCate);
            dest.writeString(this.minorCate);
            dest.writeInt(this.sizetype);
            dest.writeString(this.superscript);
            dest.writeString(this.contentType);
            dest.writeByte(this.allowMonthly ? (byte) 1 : (byte) 0);
            dest.writeInt(this.banned);
            dest.writeInt(this.latelyFollower);
            dest.writeString(this.retentionRatio);
            dest.writeString(this.lastChapter);
            dest.writeStringList(this.tags);
        }

        public BooksBean() {
        }

        protected BooksBean(Parcel in) {
            this._id = in.readString();
            this.title = in.readString();
            this.author = in.readString();
            this.shortIntro = in.readString();
            this.cover = in.readString();
            this.site = in.readString();
            this.majorCate = in.readString();
            this.minorCate = in.readString();
            this.sizetype = in.readInt();
            this.superscript = in.readString();
            this.contentType = in.readString();
            this.allowMonthly = in.readByte() != 0;
            this.banned = in.readInt();
            this.latelyFollower = in.readInt();
            this.retentionRatio = in.readString();
            this.lastChapter = in.readString();
            this.tags = in.createStringArrayList();
        }

        public static final Parcelable.Creator<BooksBean> CREATOR = new Parcelable.Creator<BooksBean>() {
            @Override
            public BooksBean createFromParcel(Parcel source) {
                return new BooksBean(source);
            }

            @Override
            public BooksBean[] newArray(int size) {
                return new BooksBean[size];
            }
        };
    }
}
