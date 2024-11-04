<template>
  <div>
    <h1>Edit Post</h1>
    <form @submit.prevent="updatePost">
      <input v-model="post.title" placeholder="Title" required />
      <textarea v-model="post.content" placeholder="Content" required></textarea>
      <button type="submit">Update Post</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      post: {
        id: null,
        title: '',
        content: ''
      }
    };
  },
  methods: {
    fetchPost() {
      const postId = this.$route.params.id;
      axios.get(`/api/posts/${postId}`)
        .then(response => {
          this.post = response.data;
        })
        .catch(error => {
          console.error('Error fetching post:', error);
        });
    },
    updatePost() {
      axios.put(`/api/posts/${this.post.id}`, this.post)
        .then(() => {
          this.$router.push({ name: 'PostManagement' });
        })
        .catch(error => {
          console.error('Error updating post:', error);
        });
    }
  },
  created() {
    this.fetchPost();
  }
};
</script>