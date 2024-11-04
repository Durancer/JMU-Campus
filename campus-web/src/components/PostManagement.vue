<template>
  <div>
    <h1>Post Management</h1>
    <div v-for="post in posts" :key="post.id">
      <h2>{{ post.title }}</h2>
      <p>{{ post.content }}</p>
      <p>{{ post.createdAt }}</p>
      <button @click="editPost(post)">Edit</button>
      <button @click="deletePost(post.id)">Delete</button>
    </div>
    <form @submit.prevent="createPost">
      <input v-model="newPost.title" placeholder="Title" required />
      <textarea v-model="newPost.content" placeholder="Content" required></textarea>
      <button type="submit">Create Post</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      posts: [],
      newPost: {
        title: '',
        content: ''
      }
    };
  },
  methods: {
    fetchPosts() {
      axios.get('/api/posts')
        .then(response => {
          this.posts = response.data;
        })
        .catch(error => {
          console.error('Error fetching posts:', error);
        });
    },
    createPost() {
      axios.post('/api/posts', this.newPost)
        .then(() => {
          this.fetchPosts();
          this.newPost.title = '';
          this.newPost.content = '';
        })
        .catch(error => {
          console.error('Error creating post:', error);
        });
    },
    editPost(post) {
      this.$router.push({ name: 'EditPost', params: { id: post.id } });
    },
    deletePost(id) {
      axios.delete(`/api/posts/${id}`)
        .then(() => {
          this.fetchPosts();
        })
        .catch(error => {
          console.error('Error deleting post:', error);
        });
    }
  },
  created() {
    this.fetchPosts();
  }
};
</script>