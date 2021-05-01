/**
  * Copyright 2021 bejson.com 
  */
package ncm.jsonSupp;

/**
 * Auto-generated: 2021-01-24 7:19:10
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class CommentDatas {

    private UserProfile userProfile;
    private String content;
    private String programName;
    private long programId;
    private long commentId;
    public void setUserProfile(UserProfile userProfile) {
         this.userProfile = userProfile;
     }
     public UserProfile getUserProfile() {
         return userProfile;
     }

    public void setContent(String content) {
         this.content = content;
     }
     public String getContent() {
         return content;
     }

    public void setProgramName(String programName) {
         this.programName = programName;
     }
     public String getProgramName() {
         return programName;
     }

    public void setProgramId(long programId) {
         this.programId = programId;
     }
     public long getProgramId() {
         return programId;
     }

    public void setCommentId(long commentId) {
         this.commentId = commentId;
     }
     public long getCommentId() {
         return commentId;
     }

}