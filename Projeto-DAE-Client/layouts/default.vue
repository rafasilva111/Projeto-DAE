<template>
  <div id="app">
    <b-navbar toggleable="lg">
      <b-navbar-brand href="/">Plataforma PRC</b-navbar-brand>
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav v-if="$auth.loggedIn">
          <b-nav-item-dropdown  right>
            <!-- Using 'button-content' slot -->
            <template #button-content>
              <em>Sinais Biomédicos</em>
            </template>
            <b-dropdown-item @click.prevent="goColestrol">Colestrol</b-dropdown-item>
            <b-dropdown-item @click.prevent="goColestrol">Peso/Altura</b-dropdown-item>
            <b-dropdown-item @click.prevent="goColestrol">BPM's</b-dropdown-item>
            <b-dropdown-item @click.prevent="goColestrol">Outros Sinais</b-dropdown-item>
            <b-dropdown-item @click.prevent="goColestrol">Ficheiros Partilhados</b-dropdown-item>
          </b-nav-item-dropdown>
          <li class="nav-item">
            <nuxt-link class="nav-link" to="students">Prescrições</nuxt-link>
          </li>
        </b-navbar-nav>
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <b-nav-item-dropdown v-if="$auth.loggedIn" right>
            <!-- Using 'button-content' slot -->
            <template #button-content>
              <em>{{ $auth.user.sub }}</em>
            </template>
            <b-dropdown-item @click.prevent="signOut">Sign Out</b-dropdown-item>
          </b-nav-item-dropdown>
          <li class="nav-item" v-else>
            <nuxt-link class="nav-link" to="/auth/login">Sign In</nuxt-link>
          </li>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <main>
      <Nuxt/>
    </main>
  </div>
</template>
<script>
export default {
  methods: {
    signOut() {
      this.$auth.logout()
      this.$router.push('/')
    },
    goColestrol() {

      this.$router.push('/biosinais/colestrol/my')
    }
  }
}
</script>
